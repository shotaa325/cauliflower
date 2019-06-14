package com.github.shotah.cauliflower.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;


@Controller
public class MainController {

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/input-batter")
    public String getInputBatter() {

        return "input-batter";
    }

    @GetMapping("/form")
    public String Post(Model model, String matchDate, int playerId, String one, String two, String three, String four,
                       String five, String six, String runBattedIn) {
//        model.addAttribute("one", one);
        // System.out.println(one);

//        String[] plateApearance = {one, two, three, four, five, six};
//
//        for(int i = 0; i<plateApearance.length; i++) {
//            if(plateApearance[i] == "2") {
//                //i番目が2(単打だったら)
//                //「安打」に1を足す
//                int hit =  jdbc.queryForList("SELECT hit FROM batter WHERE );
//                hit++;
//            }else if(plateApearance[i] == "3") {
//                //i番目が3(二塁打だったら)
//                //「安打」に1を足す
//            }else if(plateApearance[i] == "4") {
//                //i番目が4(三塁打だったら)
//                //「安打」に1を足す
//            }else if(plateApearance[i] == "5") {
//                //i番目が5(本塁打だったら)
//                //「安打」に1を足す
//            }
//        }

//        List<String> plateAppearance = new ArrayList<String>();
//        plateAppearance.add(one);
//        plateAppearance.add(two);
//        plateAppearance.add(three);
//        plateAppearance.add(four);
//        plateAppearance.add(five);
//        plateAppearance.add(six);
//            {
//                put(one);
//            }
//        };

        String[] plateAppearance = {one, two, three, four, five, six};
        for (int i = 0; i < plateAppearance.length; i++) {

            if(plateAppearance[i].equals("なし")){
                break;
            }
            jdbc.update(
                    "INSERT INTO result(match_date, player_id, plate_appearance, at_bat_times) " +
                            "VALUES(?, ?, ?, ?);", matchDate, playerId, plateAppearance[i], (i+1));
        }
        jdbc.update(
                "INSERT INTO result(match_date, player_id, run_batted_in) " +
                        "VALUES(?, ?, ?);", matchDate, playerId, runBattedIn);

        int singleHit;
        int doubleHit;
        int tripleHit;
        int homeRun;
        int bunt;
        int sacrificeFry;
        int baseOnBall;
        int hitByPitch;
        int strikeOut;
        int others;
        //int plate_appearance_time;


//        for(int i = 0; i < plateAppearance.length ; i++){
//            if(plateAppearance[i].equals("単打") || plateAppearance[i].equals("二塁打") ||plateAppearance[i].equals("三塁打") || plateAppearance[i].equals("本塁打") ){
//                hit++;
//            }
//        }

        Object num = jdbc.queryForMap("SELECT num FROM player WHERE num =VALUES(?) ", playerId).get("num");
        model.addAttribute("num", num);
        model.addAttribute("hit",jdbc.queryForMap("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);",num).get("hit"));
        model.addAttribute("homeRun",jdbc.queryForMap("SELECT COUNT(plate_appearance) AS homeRun  FROM result WHERE plate_appearance = '本塁打'  AND player_id = VALUES(?);", num) .get("homeRun"));
        jdbc.queryForMap("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = 3;");

        //試合数表示
        model.addAttribute("gameNum",jdbc.queryForMap("select count(distinct match_date) AS gameNum from result where player_id = VALUES(?);",num).get("gameNum"));
        //名前表示
        model.addAttribute("playerName",jdbc.queryForMap("select name AS playerName from player where num = VALUES(?);",num).get("playerName"));

        //int  atBat  = intValue(jdbc.queryForMap("select count(at_bat_times) from result where plate_appearance not in ('死球','四球','犠打','犠飛') AND player_id = ?;", num).get("count(at_bat_times)"));
//        Map<String, Object> atBata  = jdbc.queryForMap("select count(at_bat_times) from result where plate_appearance not in ('死球','四球','犠打','犠飛') AND player_id = ?;", num).get(0);
        //Object hit = jdbc.queryForMap("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);",num).get("hit");
       // System.out.println(atBat);

       // Object battingAverage = hit / atBat;
      //  System.out.println(battingAverage);

        System.out.println(jdbc.queryForMap("SELECT num FROM player WHERE num =VALUES(?) ", playerId));
//        System.out.println(hit);
        return "main-record-batter";
    }

    }



