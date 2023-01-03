package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.entity.ConfirmationToken;
import com.example.demo.attendence.exception.UserDoesNotExistException;
import com.example.demo.attendence.model.RegistrationRequestModel;
import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.model.UserResponseModel;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.mapper.UserMapper;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.service.EmailService;
import com.example.demo.attendence.service.UserService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final ConfirmationTokenService confirmationTokenService ;
    private final EmailService emailSender ;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final ConfirmationTokenService confirmationTokenService ;
    private UserRepository userRepository;
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    @Override
    public UserResponseModel createUser(UserRequestModel userModel) {
        System.out.println(userModel);
        User user = userMapper.userToEntity(userModel);
        System.out.println(user);
        return userMapper.userToModel(userRepository.save(user));
    }
    @Override
    public UserResponseModel updateUser(UserRequestModel userModel) {
        return null;
    }


    @Override
    public List<UserResponseModel> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserResponseModel getUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(UserDoesNotExistException::new);
        return userMapper.userToModel(user);
    }

    @Override
    public UserResponseModel getUserByUserName(String username) {
        return this.userMapper.userToModel(this.userRepository.findByUsername(username).orElseThrow());
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG , email)));

    }
    @Transactional
    public String signUpUser(User user){
        boolean existEmail = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if(existEmail){
            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

//        TODO: send confirmation token

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken =
                new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusYears(1),
                        user
                );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

//        TODO: send email

        return token ;
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
    public String register(UserRequestModel request) {

        String token = signUpUser(userMapper.userToEntity(request));
        String link = "http://localhost:8080/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getName(), link));

        return token;
    }


    public UserResponseModel getUser(){
        User currentLoginUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return this.userMapper.userToModel(currentLoginUser);
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        enableAppUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
