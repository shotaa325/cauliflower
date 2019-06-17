package com.github.shotah.cauliflower.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Controller
public class MainController {

    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/input-batter")
    public String getInputBatter() {

        return "input-batter";
    }
    @GetMapping("/main-record-batter/{name}")  //URLに表記するもの
    public String mainRecordBatter(Model model, @PathVariable String name) {

        //  背番号
        Object num = jdbc.queryForMap("SELECT num FROM player WHERE name =VALUES(?) ",name ).get("num");

        //背番号表示
        model.addAttribute("num", num);

        //安打数表示
        model.addAttribute("hit", jdbc.queryForMap("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);", num).get("hit"));

        //本塁打数表示
        model.addAttribute("homeRun", jdbc.queryForMap("SELECT COUNT(plate_appearance) AS homeRun  FROM result WHERE plate_appearance = '本塁打'  AND player_id = VALUES(?);", num).get("homeRun"));

        jdbc.queryForMap("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = 3;");

        //試合数表示
        model.addAttribute("gameNum", jdbc.queryForMap("select count(distinct match_date) AS gameNum from result where player_id = VALUES(?);", num).get("gameNum"));
        //名前表示
        model.addAttribute("playerName", jdbc.queryForMap("select name AS playerName from player where num = VALUES(?);", num).get("playerName"));

        //打数
        double atBat = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance not in ('死球','四球','犠打','犠飛') AND player_id = ?;", Integer.class, num);
        System.out.println(atBat);

        //安打数
        double hit = jdbc.queryForObject("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);", Integer.class, num);
        System.out.println(hit);

        //四球数
        double baseOnBall = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('四球') AND player_id = ?;", Integer.class, num);

        //死球数
        double hitByPitch = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('死球') AND player_id = ?;", Integer.class, num);

        //犠飛数
        double sacrificeFly = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('犠飛') AND player_id = ?;", Integer.class, num);

        //二塁打数
        double doubleHit = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('二塁打') AND player_id = ?;", Integer.class, num);

        //三塁打数
        double tripleHit = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('三塁打') AND player_id = ?;", Integer.class, num);

        //本塁打数
        double homeRun = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('本塁打') AND player_id = ?;", Integer.class, num);

        //打率計算
        double battingAverage = hit / atBat;
        System.out.println(battingAverage);
        model.addAttribute("battingAverage", battingAverage);

        //出塁率計算
        double onBasePercentage = (hit + baseOnBall + hitByPitch) / (atBat + baseOnBall + hitByPitch);

        //塁打数
        double totalBases = (hit * 1) + (doubleHit * 1) + (tripleHit * 2) + (homeRun * 3);

        //長打率計算
        double sluggingPercentage = totalBases / atBat;

        //OPS計算
        double onBasePlusSlugging = onBasePercentage + sluggingPercentage;
        if (onBasePlusSlugging >= 1) {
            model.addAttribute("onBasePlusSlugging", onBasePlusSlugging);
        } else {
            double onBasePlusSlugging2 = onBasePlusSlugging;
            model.addAttribute("onBasePlusSlugging2", onBasePlusSlugging2);
            System.out.println(onBasePlusSlugging2);
        }
        return "main-record-batter";  //実際に表示するhtml
    }

    @GetMapping("/input-player")
    public String inputPlayer(){
        return "input-player";
    }


    @PostMapping("/form")
    public String Post(String matchDate, int playerId, String one, String two, String three, String four,
                              String five, String six, String runBattedIn, RedirectAttributes attr) {

        String[] plateAppearance = {one, two, three, four, five, six};
        for (int i = 0; i < plateAppearance.length; i++) {

            if (plateAppearance[i].equals("なし")) {
                break;
            }
            jdbc.update(
                    "INSERT INTO result(match_date, player_id, plate_appearance, at_bat_times) " +
                            "VALUES(?, ?, ?, ?);", matchDate, playerId, plateAppearance[i], (i + 1));
        }
        jdbc.update(
                "INSERT INTO result(match_date, player_id, run_batted_in) " +
                        "VALUES(?, ?, ?);", matchDate, playerId, runBattedIn);

//
        //  背番号
        Object num = jdbc.queryForMap("SELECT num FROM player WHERE num =VALUES(?) ", playerId).get("num");

        //背番号表示
        attr.addFlashAttribute("num", num);

        //安打数表示
        attr.addFlashAttribute("hit", jdbc.queryForMap("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);", num).get("hit"));

        //本塁打数表示
        attr.addFlashAttribute("homeRun", jdbc.queryForMap("SELECT COUNT(plate_appearance) AS homeRun  FROM result WHERE plate_appearance = '本塁打'  AND player_id = VALUES(?);", num).get("homeRun"));

        jdbc.queryForMap("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = 3;");

        //試合数表示
        attr.addFlashAttribute("gameNum", jdbc.queryForMap("select count(distinct match_date) AS gameNum from result where player_id = VALUES(?);", num).get("gameNum"));
        //名前表示
        attr.addFlashAttribute("playerName", jdbc.queryForMap("select name AS playerName from player where num = VALUES(?);", num).get("playerName"));

        //打数
        double atBat = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance not in ('死球','四球','犠打','犠飛') AND player_id = ?;", Integer.class, num);
        System.out.println(atBat);

        //安打数
        double hit = jdbc.queryForObject("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);", Integer.class, num);
        System.out.println(hit);

        //四球数
        double baseOnBall = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('四球') AND player_id = ?;", Integer.class, num);

        //死球数
        double hitByPitch = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('死球') AND player_id = ?;", Integer.class, num);

        //犠飛数
        double sacrificeFly = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('犠飛') AND player_id = ?;", Integer.class, num);

        //二塁打数
        double doubleHit = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('二塁打') AND player_id = ?;", Integer.class, num);

        //三塁打数
        double tripleHit = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('三塁打') AND player_id = ?;", Integer.class, num);

        //本塁打数
        double homeRun = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('本塁打') AND player_id = ?;", Integer.class, num);

        //打率計算
        double battingAverage = hit / atBat;
        System.out.println(battingAverage);
        attr.addFlashAttribute("battingAverage", battingAverage);

        //出塁率計算
        double onBasePercentage = (hit + baseOnBall + hitByPitch) / (atBat + baseOnBall + hitByPitch);

        //塁打数
        double totalBases = (hit * 1) + (doubleHit * 1) + (tripleHit * 2) + (homeRun * 3);

        //長打率計算
        double sluggingPercentage = totalBases / atBat;

        //OPS計算
        double onBasePlusSlugging = onBasePercentage + sluggingPercentage;
        if (onBasePlusSlugging >= 1) {
            attr.addFlashAttribute("onBasePlusSlugging", onBasePlusSlugging);
        } else {
            double onBasePlusSlugging2 = onBasePlusSlugging;
            attr.addFlashAttribute("onBasePlusSlugging2", onBasePlusSlugging2);
            System.out.println(onBasePlusSlugging2);
        }


        System.out.println(jdbc.queryForMap("SELECT num FROM player WHERE num =VALUES(?) ", playerId));
//        System.out.println(hit);
        return "redirect:/main-record-batter";
    }

    @GetMapping("/player-list")
    public String playerList(Model model){
        List<Map<String, Object>> playerList = jdbc.queryForList("select * from player");
        for(int i=0 ; i<playerList.size(); i++){
            playerList.get(i).put("link", "main-record-batter/"+playerList.get(i).get("name"));
        }
        System.out.println(playerList);
        model.addAttribute("playerList", playerList);
        return "player-list";
    }

    @PostMapping("/inputPlayer")
    public String inputPlayer(String team, String num, String name, String position , RedirectAttributes attr){

        attr.addFlashAttribute(jdbc.update(
                "INSERT INTO player(num, name, position, team)" +
                        "VALUES(?, ?, ?, ?);", num, name, position, team));

        return "redirect:/player-list";
    }

}




