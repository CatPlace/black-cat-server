package com.spring.blackcat.user;

import com.spring.blackcat.address.Address;
import com.spring.blackcat.common.code.Role;
import com.spring.blackcat.user.dto.UserJoinReqDto;
import com.spring.blackcat.user.dto.UserJoinResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    @Override
    public UserJoinResDto join(UserJoinReqDto userJoinReqDto) {

        String id = userJoinReqDto.getId();
        String password = bCryptPasswordEncoder.encode(userJoinReqDto.getPassword());
        String name = userJoinReqDto.getName();
        // TODO: Address 선택 값으로 입력 받아 회원가입 진행
        Address address = null;
        Role role = Role.BASIC;

        User user = new User(id, password, name, role, address, "SYSTEM", "SYSTEM");

        userRepository.save(user);

        return new UserJoinResDto("success");
    }
}
