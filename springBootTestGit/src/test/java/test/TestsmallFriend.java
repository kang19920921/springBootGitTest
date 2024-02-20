package test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestsmallFriend {

    public static void main(String[] args) {
        int result = getMostReward(1, 2, 3);
        log.info("获得最多奖励={}", result);
    }


    static Integer getMostReward(int n, int l, int r) {
        int maxSurPlus = 0;

        if(n < 2){
            throw  new RuntimeException("n >= 2");
        }

        if(l >= n && l <= r){
          for(int k = l ;k<=r ;k++ ){
              int surPlus = n % k ;
              maxSurPlus = surPlus > maxSurPlus ? surPlus : maxSurPlus;
          }

        }else{
            throw  new RuntimeException("n =< l <= r" );
        }


        return maxSurPlus;
    }


}



