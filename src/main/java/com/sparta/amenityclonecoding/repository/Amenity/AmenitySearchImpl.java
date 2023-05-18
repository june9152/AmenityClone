package com.sparta.amenityclonecoding.repository.Amenity;

import antlr.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.sparta.amenityclonecoding.dto.AmenityRequestDto;
import com.sparta.amenityclonecoding.entity.Amenity;
import com.sparta.amenityclonecoding.entity.QAmenity;
import com.sparta.amenityclonecoding.entity.QReserve;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AmenitySearchImpl extends QuerydslRepositorySupport implements AmenitySearch {

    public AmenitySearchImpl() {
        super(Amenity.class);
    }

    @Override
    public List<Amenity> searchFilter(AmenityRequestDto amenityRequestDto) {
        QAmenity amenity = QAmenity.amenity;
        JPQLQuery<Amenity> query = from(amenity);

        List<String> commonList = Arrays.asList(amenityRequestDto.getAmenityCommon());
        List<String> inList = Arrays.asList(amenityRequestDto.getAmenityIn());
        List<String> etcList = Arrays.asList(amenityRequestDto.getAmenityEtc());

        List<String> common = commonList.stream().distinct().collect(Collectors.toList());
        List<String> in = inList.stream().distinct().collect(Collectors.toList());
        List<String> etc = etcList.stream().distinct().collect(Collectors.toList());

        String amenityCommon = String.join(",", common);
        String amenityIn = String.join(",", in);
        String amenityEtc = String.join(",", etc);

        query.where(
                amenityTypeEq(amenityRequestDto.getAmenityType(), amenity),
//                amenityNmEq(amenityRequestDto.getAmenityNm(), amenity),
//                amenityLocationEq(amenityRequestDto.getAmenityLocation(), amenity),
//                amenityDetailLocationEq(amenityRequestDto.getAmenityDetailLocation(), amenity),
                amenityCategoryEq(amenityRequestDto.getAmenityCategory(), amenity),
                amenityPeopleEq(amenityRequestDto.getAmenityPeople(), amenity),
                amenityValEq(amenityRequestDto.getAmenityVal(), amenity),
                amenityCommonEq(amenityCommon, amenity),
                amenityInEq(amenityIn, amenity),
                amenityEtcEq(amenityEtc, amenity)
        );

        return query.fetch();
    }

    @Override
    public List<Amenity> findByKeyword(String keyword) {
        QAmenity amenity = QAmenity.amenity;
        JPQLQuery<Amenity> query = from(amenity);

        query.where(amenity.amenityAddr.contains(keyword)
                .or(amenity.amenityAddr2.contains(keyword))
                .or(amenity.amenityNm.contains(keyword)));
        return query.fetch();
    }

    private BooleanExpression amenityTypeEq(Long amenityType, QAmenity amenity ) {
        if(amenityType == null)
            return null;
        return amenity.amenityType.eq(amenityType);
    }
    private BooleanExpression amenityNmEq(String AmenityNm, QAmenity amenity ) {
        if(AmenityNm.isEmpty())
            return null;
        return amenity.amenityNm.eq(AmenityNm);
    }

//    private BooleanExpression amenityLocationEq(String amenityLocation, QAmenity amenity ) {
//        if(amenityLocation.isEmpty())
//            return null;
//        return amenity.amenityLocation.eq(amenityLocation);
//    }
//
//    private BooleanExpression amenityDetailLocationEq(String amenityDetailLocation, QAmenity amenity ) {
//        if(amenityDetailLocation.isEmpty())
//            return null;
//        return amenity.amenityDetailLocation.eq(amenityDetailLocation);
//    }

    private BooleanExpression amenityCategoryEq(String amenityCategory, QAmenity amenity ) {
        if(amenityCategory.isEmpty())
            return null;
        return amenity.amenityCategory.eq(amenityCategory);
    }

    private BooleanExpression amenityPeopleEq(String amenityPeople, QAmenity amenity ) {
        if(amenityPeople.isEmpty())
            return null;
        return amenity.amenityPeople.eq(amenityPeople);
    }

    private BooleanExpression amenityValEq(String amenityVal, QAmenity amenity ) {
        if(amenityVal.isEmpty())
            return null;
        return amenity.amenityVal.eq(amenityVal);
    }

    private BooleanExpression amenityCommonEq(String amenityCommon, QAmenity amenity ) {
        if(amenityCommon.isEmpty())
            return null;
        return amenity.amenityCommon.contains(amenityCommon);
    }

    private BooleanExpression amenityInEq(String amenityIn, QAmenity amenity ) {
        if(amenityIn.isEmpty())
            return null;
        return amenity.amenityIn.contains(amenityIn);
    }

    private BooleanExpression amenityEtcEq(String amenityEtc, QAmenity amenity ) {
        if(amenityEtc.isEmpty())
            return null;
        return amenity.amenityEtc.contains(amenityEtc);
    }
}
