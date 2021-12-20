package com.astroenergy.java.astroenergyApplication.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.astroenergy.java.astroenergyApplication.jwt.AccountCredentials;
import com.astroenergy.java.astroenergyApplication.jwt.JwtTokenUtil;
import com.astroenergy.java.astroenergyApplication.model.Feedback;
import com.astroenergy.java.astroenergyApplication.model.Location;
import com.astroenergy.java.astroenergyApplication.model.User;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;
import com.astroenergy.java.astroenergyApplication.security.IUserService;
import com.astroenergy.java.astroenergyApplication.security.JwtUserDetailsService;
import com.astroenergy.java.astroenergyApplication.service.AWSS3Service;
import com.astroenergy.java.astroenergyApplication.service.UserProfileService;
import com.astroenergy.java.astroenergyApplication.util.GenericResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*", maxAge = 36000)
public class UserController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	
	@Autowired
	private AWSS3Service awsService; 

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AccountCredentials authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
			roles.add(grantedAuthority.getAuthority());
		}
		Map<String, Object> userMap = new HashMap<>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		User user = (User) request.getAttribute("userObj");
		if (user != null) {
			userMap.put("id", user.getId());
		}
		userMap.put("token", token);
		userMap.put("userName", userDetails.getUsername());
		userMap.put("roles", roles);
		userMap.put("enabled", user.isEnabled());
		return ResponseEntity.ok(userMap);
	}


	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@RequestMapping(value = "/saveProfile", method = RequestMethod.POST)
	public ResponseEntity<?> saveProfile(@RequestParam String userId,@RequestBody UserProfile userProfile)
			throws Exception {
		UserProfile profile = null;
		try {
			profile = userProfileService.updateUserProfile(userId, userProfile);
		} catch (Exception e) {
			return new ResponseEntity<GenericResponse>(new GenericResponse("Exception in saving job profile="+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<UserProfile>(profile,HttpStatus.CREATED);
		
	}
	@RequestMapping("/changeUserView")
	public ResponseEntity<?> changeUserView(@RequestParam Long id,@RequestParam String viewed) {
		try {
		User user = userService.changeUserView(id,viewed);
		 return new ResponseEntity<>(user, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	
	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	public ResponseEntity<?> getProfile(@RequestParam String userId)
			throws Exception {
		UserProfile profile = null;
		try {
			profile = userProfileService.getUserProfile(userId);
		} catch (Exception e) {
			return new ResponseEntity<GenericResponse>(new GenericResponse("Exception in getting job profile="+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<UserProfile>(profile,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@RequestParam String userId)
			throws Exception {
		User user = null;
		try {
			user = userProfileService.getUser(userId);
		} catch (Exception e) {
			return new ResponseEntity<GenericResponse>(new GenericResponse("Exception in getting job profile="+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public ResponseEntity<?> getUsers()
			throws Exception {
		List<User> users = null;
		try {
			users = userProfileService.getUsers();
		} catch (Exception e) {
			return new ResponseEntity<GenericResponse>(new GenericResponse("Exception in getting job profile="+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
	}
	
	
//	@GetMapping("/getprofilePic/{profilePicName}")
//	public ResponseEntity<byte[]> downloadFile(@PathVariable String profilePicName) {
//		ByteArrayOutputStream downloadInputStream = awsService.downloadFile(profilePicName);
//	
//		return ResponseEntity.ok()
//					.contentType(MediaType.IMAGE_PNG)
//					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + profilePicName + "\"")
//					.body(downloadInputStream.toByteArray());	
//	}
	
//	@GetMapping("getLocations")
//	public ResponseEntity<?> getLocations()
//	{
//		try {
//			List<Location> locations =  userProfileService.getLocations();
//			
//			return ResponseEntity.ok(locations);
//		} catch (Exception e) {
//			return new ResponseEntity<GenericResponse>(new GenericResponse("Exception in getting job profile="+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	
	

//	@GetMapping("/registrationConfirm")
//	public ResponseEntity<?> confirmRegistration(@RequestParam("token") final String token)
//			throws UnsupportedEncodingException {
//		try {
//			final String result = userService.validateVerificationToken(token);
//			if (result.equals("valid")) {
//				final User user = userService.getUser(token);
//				// if (user.isUsing2FA()) {
//				// model.addAttribute("qr", userService.generateQRUrl(user));
//				// return "redirect:/qrcode.html?lang=" + locale.getLanguage();
//				// }
//				authWithoutPassword(user);
//				user.setEnabled(true);
//				userService.saveRegisteredUser(user);
//				Map<String, Object> userMap = new HashMap<>();
//				if (user != null) {
//					userMap.put("id", user.getId());
//				}
//				userMap.put("token", token);
//				userMap.put("userName", user.getEmail());
//				userMap.put("roles", user.getRoles());
//				userMap.put("enabled", user.isEnabled());
//				return ResponseEntity.ok(userMap);
//			}
//			return new ResponseEntity("Token not valid", HttpStatus.NOT_FOUND);
//		} catch (Exception e) {
//			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}

	public void authWithoutPassword(User user) {

//	        List<Privilege> privileges = user.getRoles()
//	                .stream()
//	                .map(Role::getPrivileges)
//	                .flatMap(Collection::stream)
//	                .distinct()
//	                .collect(Collectors.toList());

		List<GrantedAuthority> authorities = user.getRoles().stream().map(p -> new SimpleGrantedAuthority(p.getName()))
				.collect(Collectors.toList());

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	
	@RequestMapping(value = "/getUsersByRole", method = RequestMethod.GET)
	public ResponseEntity<?> getUsersByRole(@RequestParam String roleName)
			throws Exception {
		List<User> users = null;
		try {
			users = userService.getUserByRoleName(roleName);
		} catch (Exception e) {
			return new ResponseEntity<GenericResponse>(new GenericResponse("Exception in fetching users="+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
	}
	
	
	
	@PostMapping("/filterEmployeesProfiles")
	public ResponseEntity<?> filterEmployeesProfiles(@RequestBody String job) {
		List<UserProfile> recruiterProfiles = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> searchCriteria = mapper.readValue(job, HashMap.class);
			List<String> tags = (List<String>) searchCriteria.get("tags");
			
			recruiterProfiles = userService.getUserProfilesByRoleParams(searchCriteria);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(
					"Exception in getting Jobs with tags with message=" + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<UserProfile>>(recruiterProfiles, HttpStatus.OK);

	}


	
	
	
	
	
	
	
	
	@RequestMapping(value = "/updateProfilePic", method = RequestMethod.POST)
	public ResponseEntity<?> updateProfilePic(@RequestPart(value= "file" ,required = true) final MultipartFile multipartFile,@RequestParam String userId)
			throws Exception {
		UserProfile profile = null;
		File file = null;
		try {
			profile = userProfileService.getUserProfile(userId);
			awsService.uploadFile(multipartFile, String.valueOf(profile.getId()));
			profile.setProfilePicName(multipartFile.getOriginalFilename());
			userProfileService.updateUserProfilePic(profile);
		} catch (Exception e) {
			return new ResponseEntity<GenericResponse>(new GenericResponse("Exception in updating profile pic"+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<UserProfile>(profile,HttpStatus.OK);
		
	}
	
	
	
	private File convertMultiPartFileToFile(final MultipartFile multipartFile) throws Exception {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			throw ex;
		}
		return file;
	}
	
	@GetMapping("/getProfilePic/{name}/{userId}")
	public ResponseEntity<byte[]> downloadFileByUserProfileId(@PathVariable String name,@PathVariable String userId ) throws Exception {
		UserProfile profile = null;
		ByteArrayOutputStream downloadInputStream = null;
		profile = userProfileService.getUserProfile(userId);
		try {
			downloadInputStream = awsService.downloadFile(name, profile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(downloadInputStream.toByteArray());
	}
	
	@GetMapping("/getProfilePicByProfileId/{name}/{userProfileId}")
	public ResponseEntity<byte[]> getProfilePicByProfileId(@PathVariable String name,@PathVariable String userProfileId ) throws Exception {
		UserProfile profile = null;
		ByteArrayOutputStream downloadInputStream = null;
		profile = userProfileService.getUserProfileById(userProfileId);
		try {
			downloadInputStream = awsService.downloadFile(name, profile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
                .contentType(contentType(name))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
                .body(downloadInputStream.toByteArray());
	}
	
	@GetMapping("/getProfilePic/{name}")
	public ResponseEntity<byte[]> downloadGenericFileByUserProfileId(@PathVariable String name ) throws Exception {
		UserProfile profile = null;
		ByteArrayOutputStream downloadInputStream = null;
		
		try {
			downloadInputStream = awsService.downloadFile(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(downloadInputStream.toByteArray());
	}
	
	
	private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
	
	public static void main(String[] args) {
		UserProfile profile = new UserProfile();
		profile.setAboutMe("Hii");
		profile.setFirstName("Akash");
		profile.setLastName("Vashisht");
		profile.setEmail("akash@gmail.com");
		profile.setPhone("9560696372");
		profile.setGender("Male");
		
          		
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(profile));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
