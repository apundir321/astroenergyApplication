package com.astroenergy.java.astroenergyApplication.security;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.NewLocationTokenRepository;
import com.astroenergy.java.astroenergyApplication.dao.PasswordResetTokenRepository;
import com.astroenergy.java.astroenergyApplication.dao.RoleRepository;
import com.astroenergy.java.astroenergyApplication.dao.UserLocationRepository;
import com.astroenergy.java.astroenergyApplication.dao.UserRepository;
import com.astroenergy.java.astroenergyApplication.dao.VerificationTokenRepository;
import com.astroenergy.java.astroenergyApplication.dto.PasswordDto;
import com.astroenergy.java.astroenergyApplication.dto.UserDto;
import com.astroenergy.java.astroenergyApplication.error.UserAlreadyExistException;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Blog;
import com.astroenergy.java.astroenergyApplication.model.NewLocationToken;
import com.astroenergy.java.astroenergyApplication.model.PasswordResetToken;
import com.astroenergy.java.astroenergyApplication.model.Role;
import com.astroenergy.java.astroenergyApplication.model.User;
import com.astroenergy.java.astroenergyApplication.model.UserLocation;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;
import com.astroenergy.java.astroenergyApplication.model.VerificationToken;


@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private SessionRegistry sessionRegistry;

//    @Autowired
//    @Qualifier("GeoIPCountry")
//    private DatabaseReader databaseReader;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private NewLocationTokenRepository newLocationTokenRepository;
    
    @Autowired
    private JavaMailSender mailSender;
    private final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Autowired
    private Environment env;
    
    @Value("${spring.mail.forgotPasswordUrl}")
    private String forgotPasswordUrl;
    
    @Value("${spring.mail.username}")
    private String fromMail;
    
    private static final Logger log= LogManager.getLogger(UserService.class);

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "SpringRegistration";

    // API

    @Override
    public User registerNewUserAccount(final UserDto accountDto,boolean isRecuiter) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        Role role = null;
        UserProfile profile = null;
        
        final User user = new User();
        List<Role> roles = new ArrayList<Role>();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setCountryCode(accountDto.getCountryCode());
        user.setPhoneNo(accountDto.getPhoneNo());
        user.setUsing2FA(accountDto.isUsing2FA());	
        	profile = new UserProfile();
        	profile.setEmail(accountDto.getEmail());
            profile.setCountryCode(accountDto.getCountryCode());
        	profile.setFirstName(accountDto.getFirstName());
        	profile.setLastName(accountDto.getLastName());
        	profile.setPhone(accountDto.getPhoneNo());
        	
        	if(role==null)
        	{
        		role = new Role();
        		role.setName("ROLE_USER");
        		roleRepository.save(role);
        	}
        	roles.add(role);
        
        user.setRoles(roles);
        user.setUserProfile(profile);
        return userRepository.save(user);
    }
    public User registerNewUserAccount(Appointment appointment) {
        if (emailExists(appointment.getEmail())) {
            return null;
        }
        Role role = null;
        UserProfile profile = null;
        
        final User user = new User();
        List<Role> roles = new ArrayList<Role>();
        user.setFirstName(appointment.getName());
        user.setPassword(passwordEncoder.encode(appointment.getEmail()));
        user.setEmail(appointment.getEmail());
        user.setCountryCode(appointment.getCountry());
        	profile = new UserProfile();
        	profile.setEmail(appointment.getEmail());
        	profile.setFirstName(appointment.getName());
    
        	role = roleRepository.findByName("ROLE_USER");
        	if(role==null)
        	{
        		role = new Role();
        		role.setName("ROLE_USER");
        		roleRepository.save(role);
        	}
        	roles.add(role);
        
        user.setRoles(roles);
        user.setUserProfile(profile);
        return userRepository.save(user);
    }
    public String emailVerification(String token) throws Exception {
    	try {
    	VerificationToken userToken=	tokenRepository.findByToken(token);
    	if(userToken!=null && userToken.getExpiryDate().after(new Date())) {
           User user=userToken.getUser();
             user.setEnabled(true);
           userRepository.save(user);
           userToken.setExpiryDate(new Date());
           return "Verified";
    	}else{throw new Exception("Invalid or expired token");
    		}
    	}
    	catch(Exception e) {
    		throw e;
    	}
    }

    @Override
    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public User changeUserView(Long id,String viewed)  {
    	try {
    		User u=userRepository.findById(id).get();
    		u.setViewed(viewed);
    	return userRepository.save(u);
    	}
    	catch(Exception e) {
    		throw e;
    	}
    }
    @Override
    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }

//    @Override
//    public void deleteUser(final User user) {
//        final VerificationToken verificationToken = tokenRepository.findByUser(user);
//
//        if (verificationToken != null) {
//            tokenRepository.delete(verificationToken);
//        }
//
//        final PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);
//
//        if (passwordToken != null) {
//            passwordTokenRepository.delete(passwordToken);
//        }
//
//        userRepository.delete(user);
//    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
            .toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token) .getUser());
    }

    @Override
    public Optional<User> getUserByID(final long id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
            .getTime() - cal.getTime()
            .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }
    
   

    @Override
    public String generateQRUrl(User user) throws UnsupportedEncodingException {
        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
    }

    @Override
    public User updateUser2FA(boolean use2FA) {
        final Authentication curAuth = SecurityContextHolder.getContext()
            .getAuthentication();
        User currentUser = (User) curAuth.getPrincipal();
        currentUser.setUsing2FA(use2FA);
        currentUser = userRepository.save(currentUser);
        final Authentication auth = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(), curAuth.getAuthorities());
        SecurityContextHolder.getContext()
            .setAuthentication(auth);
        return currentUser;
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

//    @Override
//    public List<String> getUsersFromSessionRegistry() {
//        return sessionRegistry.getAllPrincipals()
//            .stream()
//            .filter((u) -> !sessionRegistry.getAllSessions(u, false)
//                .isEmpty())
//            .map(o -> {
//                if (o instanceof User) {
//                    return ((User) o).getEmail();
//                } else {
//                    return o.toString()
//            ;
//                }
//            }).collect(Collectors.toList());
//    }

//    @Override
//    public NewLocationToken isNewLoginLocation(String username, String ip) {
//
//        if(!isGeoIpLibEnabled()) {
//            return null;
//        }
//
//        try {
//            final InetAddress ipAddress = InetAddress.getByName(ip);
//            final String country = databaseReader.country(ipAddress)
//                .getCountry()
//                .getName();
//            System.out.println(country + "====****");
//            final User user = userRepository.findByEmail(username);
//            final UserLocation loc = userLocationRepository.findByCountryAndUser(country, user);
//            if ((loc == null) || !loc.isEnabled()) {
//                return createNewLocationToken(country, user);
//            }
//        } catch (final Exception e) {
//            return null;
//        }
//        return null;
//    }

    @Override
    public String isValidNewLocationToken(String token) {
        final NewLocationToken locToken = newLocationTokenRepository.findByToken(token);
        if (locToken == null) {
            return null;
        }
        UserLocation userLoc = locToken.getUserLocation();
        userLoc.setEnabled(true);
        userLoc = userLocationRepository.save(userLoc);
        newLocationTokenRepository.delete(locToken);
        return userLoc.getCountry();
    }

//    @Override
//    public void addUserLocation(User user, String ip) {
//
//        if(!isGeoIpLibEnabled()) {
//            return;
//        }
//
//        try {
//            final InetAddress ipAddress = InetAddress.getByName(ip);
//            final String country = databaseReader.country(ipAddress)
//                .getCountry()
//                .getName();
//            UserLocation loc = new UserLocation(country, user);
//            loc.setEnabled(true);
//            userLocationRepository.save(loc);
//        } catch (final Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    private boolean isGeoIpLibEnabled() {
        return Boolean.parseBoolean(env.getProperty("geo.ip.lib.enabled"));
    }

    private NewLocationToken createNewLocationToken(String country, User user) {
        UserLocation loc = new UserLocation(country, user);
        loc = userLocationRepository.save(loc);

        final NewLocationToken token = new NewLocationToken(UUID.randomUUID()
            .toString(), loc);
        return newLocationTokenRepository.save(token);
    }

	@Override
	public List<User> getUserByRoleName(String roleName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserProfile> getUserProfilesByRoleParams(Map<String, Object> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getRecruiter(String recruiterProfileId) {
		// TODO Auto-generated method stub
		return null;
	}
	public User deleteUser(Long id) throws Exception  {
		try {
			User u=userRepository.findByIdAndDeletedAtIsNull(id);
			u.setStatus("DELETED");
            u.setDeletedAt(new Date());
			u.setEnabled(false);
            return userRepository.save(u);
		}
		catch(Exception e) {
			throw e;
		}
	}
	public User getUserByProfile(int profileId) {
		try {
			User u=userRepository.findByUserProfileIdAndDeletedAtIsNull(profileId);
		   return u;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Override
	public void sendMailForForgotPasswordToUser(String email) throws Exception{
		try {
		User user=userRepository.findByEmail(email);
		if(user!=null) {
			String token=generateRandomAlphaNumeric(9);
			user.setForgotPasswordKey(token);
			SimpleMailMessage mail=createMailForForgotPassword(user.getEmail(),token);
			mailSender.send(mail);
		}
		else {
			throw new Exception("User doesn't exists with this email address or forgot request already made");
		}
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public SimpleMailMessage createMailForForgotPassword(String email,String token) {
		try {
			String subject="Reset Password";
			String message="Please click on the below link to reset the password for your Astro Energy account \r\n \r\n";
		    String resetLink=forgotPasswordUrl+"?key="+token;
		    SimpleMailMessage mail=new SimpleMailMessage();
		    mail.setFrom(fromMail);
		    mail.setTo(email);
		    mail.setSubject(subject);
		    mail.setText(message+resetLink);
		    return mail;
		}
		catch(Exception e) {
			throw e;
		}
	}
    public String generateRandomAlphaNumeric(int length) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
    
    @Override
    public String resetUserPassword(PasswordDto passwordDto) throws Exception{
    	try {
//    		if(passwordDto.getToken()==null) {
//    			log.error("key cannot be null");
//    			throw new Exception("Key cannot be null");
//    		}
    		if(passwordDto.getToken()==null || passwordDto.getNewPassword()==null) {
    			log.error("token or new password field could not be null");
    			throw new Exception("token or new password field could not be null");
    		}
    		User user=userRepository.findByForgotPasswordKey(passwordDto.getToken());
    		if(user==null) {
			log.error("Provided key doesn't match with any user or key might be null");
    			throw new Exception("Provided key doesn't match with any user or key might be null");
    		}
    		user.setForgotPasswordKey(null);
    		changeUserPassword(user,passwordDto.getNewPassword());
    		return "Password changed successfully";
    		
    		
    	}
    	catch(Exception e) {
    		throw e;
    	}
    }
}
