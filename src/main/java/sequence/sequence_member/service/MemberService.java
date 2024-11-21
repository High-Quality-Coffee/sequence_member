package sequence.sequence_member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sequence.sequence_member.dto.LoginDTO;
import sequence.sequence_member.dto.MemberDTO;
import sequence.sequence_member.entity.*;
import sequence.sequence_member.repository.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;
    private final AwardRepository awardRepository;
    private final CareerRepository careerRepository;
    private final EducationRepository educationRepository;
    private final ExperienceRepository experienceRepository;

    public void save(MemberDTO memberDTO){
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        ExperienceEntity experienceEntity = ExperienceEntity.toExperienceEntity(memberDTO);
        EducationEntity educationEntity = EducationEntity.toEducationEntity(memberDTO);
        CareerEntity careerEntity = CareerEntity.toCareerEntity(memberDTO);
        AwardEntity awardEntity = AwardEntity.toAwardEntity(memberDTO);

        memberRepository.save(memberEntity);
        experienceRepository.save(experienceEntity);
        educationRepository.save(educationEntity);
        careerRepository.save(careerEntity);
        awardRepository.save(awardEntity);
    }

    public LoginDTO loginCheck(LoginDTO loginDTO){
        Optional<MemberEntity> byMemberEntity =  memberRepository.findByUserId(loginDTO.getUser_id());
        if(byMemberEntity.isPresent()){
            MemberEntity memberEntity =  byMemberEntity.get();
            if(memberEntity.getUserPwd().equals(loginDTO.getUser_pwd())){
                //로그인 성공 시 처리 로직
                LoginDTO loginSuccessDTO = LoginDTO.toLoginDTO(memberEntity);
                return loginSuccessDTO;
            } else return null;
        }else return null;
    }
}
