package com.vijay.sfcp.obrs.common.utils;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 07 June 2020
*/

import com.vijay.sfcp.obrs.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public class CommonUtil {
    private static final Integer ONE = 1;
    private static final Integer TWO = 2;
    private static final Integer THREE = 3;
    private static final Integer FOUR = 4;
    private static final Integer FIVE = 5;

    public static <T, x> void pageModel(T x, Model model, Page<?> pages,String url) {
        PageWrapper<x> page = new PageWrapper(pages, url);
        model.addAttribute("page", page);
    }

    public static void ratingCalculate(List<Review> allById,Model model){
        long oneStar = allById.stream()
                .filter(review -> review.getRating() == ONE)
                .count();

        long twoStar = allById.stream()
                .filter(review -> review.getRating() == TWO)
                .count();

        long threeStar = allById.stream()
                .filter(review -> review.getRating() == THREE)
                .count();

        long fourStar = allById.stream()
                .filter(review -> review.getRating() == FOUR)
                .count();

        long fiveStar = allById.stream()
                .filter(review -> review.getRating() == FIVE)
                .count();

        long totalNoOfReview = oneStar + twoStar + threeStar + fourStar + fiveStar;

        System.out.println("CommonUtil.ratingCalculate - totalNoOfReview = "+totalNoOfReview);

        model.addAttribute("oneStar",oneStar);
        long oneStarPer = calculatePercentage(oneStar,totalNoOfReview);
        model.addAttribute("oneStarPer",oneStarPer);
        System.out.println("CommonUtil.ratingCalculate - oneStar = "+oneStar+" oneStarPer = "+oneStarPer);

        model.addAttribute("twoStar",twoStar);
        long twoStarPer = calculatePercentage(twoStar,totalNoOfReview);
        model.addAttribute("twoStarPer",twoStarPer);
        System.out.println("CommonUtil.ratingCalculate - twoStar = "+twoStar+" twoStarPer = "+twoStarPer);

        model.addAttribute("threeStar",threeStar);
        long threeStarPer = calculatePercentage(threeStar,totalNoOfReview);
        model.addAttribute("threeStarPer",threeStarPer);
        System.out.println("CommonUtil.ratingCalculate - threeStar = "+threeStar+" threeStarPer = "+threeStarPer);

        model.addAttribute("fourStar",fourStar);
        long fourStarPer = calculatePercentage(fourStar,totalNoOfReview);
        model.addAttribute("fourStarPer",fourStarPer);
        System.out.println("CommonUtil.ratingCalculate - fourStar = "+fourStar+" fourStarPer = "+fourStarPer);

        model.addAttribute("fiveStar",fiveStar);
        long fiveStarPer = calculatePercentage(fiveStar,totalNoOfReview);
        model.addAttribute("fiveStarPer",fiveStarPer);
        System.out.println("CommonUtil.ratingCalculate - fiveStar = "+fiveStar+" fiveStarPer = "+fiveStarPer);

        long oneTotal = oneStar * ONE;
        long twoTotal = twoStar * TWO;
        long threeTotal = threeStar * THREE;
        long fourTotal = fourStar * FOUR;
        long fiveTotal = fiveStar * FIVE;


        long totalNoOfStars = oneTotal + twoTotal + threeTotal + fourTotal + fiveTotal;
        System.out.println("CommonUtil.ratingCalculate - totalNoOfStars = "+totalNoOfStars);

        model.addAttribute("totalNoOfReview",totalNoOfReview);
        long avgRating = 0;

        if(totalNoOfReview !=0)
            avgRating = (totalNoOfStars/totalNoOfReview);

        model.addAttribute("avgRating",avgRating);
        model.addAttribute("avgRatingPer",avgRating);
        System.out.println("CommonUtil.ratingCalculate - avgRating = "+avgRating);
    }

    public static long calculatePercentage(long x, long y) {
        if(y !=0)
            return x * 100l / y;
        else
            return 0;
    }
}
