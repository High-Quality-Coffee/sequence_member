package sequence.sequence_member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sequence.sequence_member.dto.CustomUserDetails;
import sequence.sequence_member.entity.MemberEntity;
import sequence.sequence_member.repository.MemberRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> memberData =  memberRepository.findByUsername(username);

        if(memberData.isPresent()){
            return new CustomUserDetails(memberData.get());
        }

        return null;
    }
}