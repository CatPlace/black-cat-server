package com.spring.blackcat.address;

import com.spring.blackcat.common.BaseTimeEntity;
import com.spring.blackcat.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id; // 주소ID
    //    private String zipCode; // 우편번호
    private String sido; // 시도
    private String sidoEn; // 시도영문
//    private String sigungu; // 시군구
//    private String sigunguEn; // 시군구영문
//    private String upmyen; // 읍면
//    private String upmyenEn; // 읍면영문
//    private String loadNmCd; // 도로명코드
//    private String loadNm; // 도로명
//    private String loadNmEn; // 도로명영문
//    private String under_yn; // 지하여부
//    private Long buildingNoBon; // 건물번호본번
//    private Long buildingNoBu; // 건물번호부번
//    private Long buildingManageNo; // 건물관리번호
//    private String manyDeliveryNm; // 다량배달처명
//    private String sigunguBuildingNm; // 시군구용건물명
//    private String bupjeungdongCd; // 법정동코드
//    private String bupjeungdongNm; // 법정동명
//    private String leeNm; // 리명
//    private String hangjeungdongNm; // 행정동명
//    private String mountainYn; // 산여부
//    private Long jibunbonbun; // 지번본번
//    private String oldZipCode; // 구우편번호
//    private String zipCodeNo; // 우편번호일련번호

    @OneToMany(mappedBy = "address", cascade = ALL)
    private List<User> users;

    private String registerId;
    private String modifierId;

    public Address(String sido, String sidoEn, String registerId, String modifierId) {
        this.sido = sido;
        this.sidoEn = sidoEn;
        this.registerId = registerId;
        this.modifierId = modifierId;
    }
}
