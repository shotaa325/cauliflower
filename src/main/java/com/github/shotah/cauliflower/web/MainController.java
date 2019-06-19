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

    @GetMapping("/input-batter/{name}/{playerId}")
    public String getInputBatter(Model model, @PathVariable String name, @PathVariable int playerId) {
        System.out.println(name);
        System.out.println(playerId);
        model.addAttribute("name", name);
        model.addAttribute("playerId", playerId);
        return "input-batter";
    }
    //input.batter → main-record遷移
//    @GetMapping("/main-record-batter")
//    public String mainRecord(){
//        return "main-record-batter";
//    }

//    @GetMapping("/main-record-batter/{name}")  //URLに表記するもの
//    public String mainRecordBatter(Model model, @PathVariable String name) {
//
//        //  背番号
//        Object num = jdbc.queryForMap("SELECT num FROM player WHERE name =VALUES(?) ",name ).get("num");
//
//        //背番号表示
//        model.addAttribute("num", num);
//
//        //安打数表示
//        model.addAttribute("hit", jdbc.queryForMap("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);", num).get("hit"));
//
//        //本塁打数表示
//        model.addAttribute("homeRun", jdbc.queryForMap("SELECT COUNT(plate_appearance) AS homeRun  FROM result WHERE plate_appearance = '本塁打'  AND player_id = VALUES(?);", num).get("homeRun"));
//
//        //打点
//        model.addAttribute("runBattedIn", jdbc.queryForMap("select sum(run_batted_in) as runBattedIn from result where player_id = ?;", num).get("runBattedIn"));
//
////        jdbc.queryForMap("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = 3;");
//
//        //試合数表示
//        model.addAttribute("gameNum", jdbc.queryForMap("select count(distinct match_date) AS gameNum from result where player_id = VALUES(?);", num).get("gameNum"));
//        //名前表示
//        model.addAttribute("playerName", jdbc.queryForMap("select name AS playerName from player where num = VALUES(?);", num).get("playerName"));
//
//        //打数
//        double atBat = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance not in ('死球','四球','犠打','犠飛') AND player_id = ?;", Integer.class, num);
//        System.out.println(atBat);
//
//        //安打数
//        double hit = jdbc.queryForObject("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);", Integer.class, num);
//        System.out.println(hit);
//
//        //四球数
//        double baseOnBall = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('四球') AND player_id = ?;", Integer.class, num);
//
//        //死球数
//        double hitByPitch = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('死球') AND player_id = ?;", Integer.class, num);
//
//        //犠飛数
//        double sacrificeFly = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('犠飛') AND player_id = ?;", Integer.class, num);
//
//        //二塁打数
//        double doubleHit = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('二塁打') AND player_id = ?;", Integer.class, num);
//
//        //三塁打数
//        double tripleHit = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('三塁打') AND player_id = ?;", Integer.class, num);
//
//        //本塁打数
//        double homeRun = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance in ('本塁打') AND player_id = ?;", Integer.class, num);
//
//        //打率計算
//        double battingAverage = hit / atBat;
//        System.out.println(battingAverage);
//        model.addAttribute("battingAverage", battingAverage);
//
//        //出塁率計算
//        double onBasePercentage = (hit + baseOnBall + hitByPitch) / (atBat + baseOnBall + hitByPitch);
//
//        //塁打数
//        double totalBases = (hit * 1) + (doubleHit * 1) + (tripleHit * 2) + (homeRun * 3);
//
//        //長打率計算
//        double sluggingPercentage = totalBases / atBat;
//
//        //OPS計算
//        double onBasePlusSlugging = onBasePercentage + sluggingPercentage;
//        if (onBasePlusSlugging >= 1) {
//            model.addAttribute("onBasePlusSlugging", onBasePlusSlugging);
//        } else {
//            double onBasePlusSlugging2 = onBasePlusSlugging;
//            model.addAttribute("onBasePlusSlugging2", onBasePlusSlugging2);
//            System.out.println(onBasePlusSlugging2);
//        }
//        return "main-record-batter";  //実際に表示するhtml
//    }


    @GetMapping("/input-player")
    public String inputPlayer() {
        return "input-player";
    }


    @PostMapping("/form")
    public String Post(String matchDate, int playerId, String one, String two, String three, String four,
                       String five, String six, String runBattedIn, RedirectAttributes attr) {
        System.out.println("test1");

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


        System.out.println("player-list遷移テスト");
        return "redirect:/player-list";
    }

    @GetMapping("/player-list")
    public String playerList(Model model) {
        List<Map<String, Object>> playerList = jdbc.queryForList("select * from player");
        System.out.println("test2");

        for (int i = 0; i < playerList.size(); i++) {
            int playerId = Integer.parseInt(playerList.get(i).get("id").toString());
            //打数
            double atBat = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance not in ('死球','四球','犠打','犠飛') AND player_id = ?;", Integer.class, playerId);
            //安打数
            double hit = jdbc.queryForObject("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);", Integer.class, playerId);
            //四球数
            double baseOnBall = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('四球') AND player_id = ?;", Integer.class, playerId);
            //死球数
            double hitByPitch = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('死球') AND player_id = ?;", Integer.class, playerId);
            //犠飛数
            double sacrificeFly = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('犠飛') AND player_id = ?;", Integer.class, playerId);
            //二塁打数
            double doubleHit = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('二塁打') AND player_id = ?;", Integer.class, playerId);
            //三塁打数
            double tripleHit = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('三塁打') AND player_id = ?;", Integer.class, playerId);
            //本塁打数
            int homeRun = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('本塁打') AND player_id = ?;", Integer.class, playerId);
            //試合数
            int countGame = jdbc.queryForObject("SELECT count(distinct match_date) AS count_game FROM result WHERE player_id = ?;", Integer.class,playerId);
            //打率計算
            double battingAverage = hit / atBat;
            //出塁率計算
            double onBasePercentage = (hit + baseOnBall + hitByPitch) / (atBat + baseOnBall + hitByPitch);
            //塁打数
            double totalBases = (hit * 1) + (doubleHit * 1) + (tripleHit * 2) + (homeRun * 3);
            //長打率計算
            double sluggingPercentage = totalBases / atBat;
            //OPS計算
            double onBasePercentagePlusSlugging = onBasePercentage + sluggingPercentage;

            playerList.get(i).put("link", "input-batter/" + playerList.get(i).get("name") + "/" + playerList.get(i).get("id"));
            playerList.get(i).put("countGame", countGame);
            if (true == Double.isNaN(battingAverage)) {
                playerList.get(i).put("battingAverage", null);
            } else {
                playerList.get(i).put("battingAverage", battingAverage);
            }
            if (countGame == 0) {
                playerList.get(i).put("homeRun", null);
            } else {
                playerList.get(i).put("homeRun", homeRun);
            }
            playerList.get(i).put("runBattedIn", jdbc.queryForMap("SELECT sum(run_batted_in) AS run_batted_in FROM result WHERE player_id = ?;", playerId).get("run_batted_in"));
//            if(onBasePercentagePlusSlugging >= 1) {
            if (true == Double.isNaN(onBasePercentagePlusSlugging)) {
                playerList.get(i).put("onBasePercentagePlusSlugging2", null);
                playerList.get(i).put("onBasePercentagePlusSlugging", null);
                playerList.get(i).put("nanJudge", null);
            } else if (onBasePercentagePlusSlugging >= 1) {
                System.out.println("puttest");
                playerList.get(i).put("nanJudge", "ok");
                playerList.get(i).put("onBasePercentagePlusSlugging2", null);
                playerList.get(i).put("onBasePercentagePlusSlugging", onBasePercentagePlusSlugging);
            } else {
                playerList.get(i).put("nanJudge", "ok");
                playerList.get(i).put("onBasePercentagePlusSlugging", null);
                playerList.get(i).put("onBasePercentagePlusSlugging2", onBasePercentagePlusSlugging);
            }


            System.out.println("打率" + battingAverage);
            System.out.println("本塁打" + homeRun);
        }
        System.out.println(playerList);
        model.addAttribute("playerList", playerList);
        return "player-list";
    }

    @PostMapping("/inputPlayer")
    public String inputPlayer(String team, String num, String name, String position, RedirectAttributes attr) {

        attr.addFlashAttribute(jdbc.update(
                "INSERT INTO player(num, name, position, team)" +
                        "VALUES(?, ?, ?, ?);", num, name, position, team));

        return "redirect:/player-list";
    }


// if(check[0]!=null)
//
//    {
//        for (int i = 0; i < check.length; i++) {
//            jdbc.queryForMap("DELETE FROM player WHERE id =?; ", check[i]);
//        }
//    }

    @GetMapping("/delete")
    public String playerListDelete(Model model, int[] check) {
        System.out.println("test2");
        System.out.println(check[0]);
        for (int i = 0; i < check.length; i++) {
            jdbc.update("DELETE FROM player WHERE id =?; ", check[i]);
        }
        List<Map<String, Object>> playerList = jdbc.queryForList("select * from player");

        for (int i = 0; i < playerList.size(); i++) {
            int playerId = Integer.parseInt(playerList.get(i).get("id").toString());
            //打数
            double atBat = jdbc.queryForObject("select count(at_bat_times) from result where plate_appearance not in ('死球','四球','犠打','犠飛') AND player_id = ?;", Integer.class, playerId);
            //安打数
            double hit = jdbc.queryForObject("SELECT COUNT(plate_appearance) AS hit  FROM result WHERE plate_appearance IN ('単打', '二塁打', '三塁打', '本塁打')  AND player_id = VALUES(?);", Integer.class, playerId);
            //四球数
            double baseOnBall = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('四球') AND player_id = ?;", Integer.class, playerId);
            //死球数
            double hitByPitch = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('死球') AND player_id = ?;", Integer.class, playerId);
            //犠飛数
            double sacrificeFly = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('犠飛') AND player_id = ?;", Integer.class, playerId);
            //二塁打数
            double doubleHit = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('二塁打') AND player_id = ?;", Integer.class, playerId);
            //三塁打数
            double tripleHit = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('三塁打') AND player_id = ?;", Integer.class, playerId);
            //本塁打数
            int homeRun = jdbc.queryForObject("SELECT count(at_bat_times) FROM result where plate_appearance in ('本塁打') AND player_id = ?;", Integer.class, playerId);
            //試合数
            int countGame = jdbc.queryForObject("SELECT count(distinct match_date) AS count_game FROM result WHERE player_id = ?;", Integer.class,playerId);
            //打率計算
            double battingAverage = hit / atBat;
            //出塁率計算
            double onBasePercentage = (hit + baseOnBall + hitByPitch) / (atBat + baseOnBall + hitByPitch);
            //塁打数
            double totalBases = (hit * 1) + (doubleHit * 1) + (tripleHit * 2) + (homeRun * 3);
            //長打率計算
            double sluggingPercentage = totalBases / atBat;
            //OPS計算
            double onBasePercentagePlusSlugging = onBasePercentage + sluggingPercentage;
            Double onBasePercentagePlusSlugging2 = null;
            playerList.get(i).put("link", "input-batter/" + playerList.get(i).get("name") + "/" + playerList.get(i).get("id"));
            playerList.get(i).put("countGame", countGame);
            if (true == Double.isNaN(battingAverage)) {
                playerList.get(i).put("battingAverage", null);
            } else {
                playerList.get(i).put("battingAverage", battingAverage);
            }
            if (countGame == 0) {
                playerList.get(i).put("homeRun", null);
            } else {
                playerList.get(i).put("homeRun", homeRun);
            }
            playerList.get(i).put("runBattedIn", jdbc.queryForMap("SELECT sum(run_batted_in) AS run_batted_in FROM result WHERE player_id = ?;", playerId).get("run_batted_in"));
//            if(onBasePercentagePlusSlugging >= 1) {
//            playerList.get(i).put("onBasePercentagePlusSlugging", onBasePercentagePlusSlugging);
            if (true == Double.isNaN(onBasePercentagePlusSlugging)) {
                playerList.get(i).put("onBasePercentagePlusSlugging2", null);
                playerList.get(i).put("onBasePercentagePlusSlugging", null);
                playerList.get(i).put("nanJudge", null);
            } else if (onBasePercentagePlusSlugging >= 1) {
                System.out.println("puttest");
                playerList.get(i).put("nanJudge", "ok");
                playerList.get(i).put("onBasePercentagePlusSlugging2", null);
                playerList.get(i).put("onBasePercentagePlusSlugging", onBasePercentagePlusSlugging);
            } else {
                playerList.get(i).put("nanJudge", "ok");
                playerList.get(i).put("onBasePercentagePlusSlugging", null);
                playerList.get(i).put("onBasePercentagePlusSlugging2", onBasePercentagePlusSlugging);
            }


            System.out.println("打率" + battingAverage);
            System.out.println("本塁打" + homeRun);
        }
        System.out.println(playerList);
        model.addAttribute("playerList", playerList);
        return "player-list";
    }
}