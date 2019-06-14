CREATE TABLE batter (
    id INT AUTO_INCREMENT,
    player_id INT, --試合日
    plate_appearance INT, --打席数
    at_bat INT, --  打数
    hit INT, --安打
    two_base_hit INT, --2塁打
    three_base_hit INT, --3塁打
    home_run  INT, --本塁打
    run INT, --得点
    run_batted_in INT, --打点
    strike_out INT, --三振
    base_on_ball INT, --四球
    hit_by_pitch INT, --死球
    stolen_base INT, --盗塁
    bunt INT, --犠打
    sacrifice_fly INT, --犠飛
    batting_average DECIMAL(4,3), --打率
    on_base_percentage DECIMAL(4,3), --出塁率
    slugging_percentage DECIMAL(4,3), --長打率
    ops DECIMAL(4,3) --OPS
);

CREATE TABLE pitcher (
    id INT AUTO_INCREMENT,
    win INT,
    lose INT,
    inning_pitched INT, --イニング
    run INT, --失点
    earned_run INT, --自責点
    strike_out INT, --奪三振
    base_on_ball INT, --四球
    hit_by_pitch INT, --死球
    earned_run_average DECIMAL(4,3), --防御率
    batting_average DECIMAL(4,3), --被打率
    home_run INT, --被本塁打
    whip DECIMAL(4,3) --WHIP
);

CREATE TABLE player (
    id INT AUTO_INCREMENT,
    num VARCHAR(4),
    name VARCHAR(30),
    position VARCHAR(30),
    team VARCHAR(30)
);

CREATE TABLE result (
    id INT AUTO_INCREMENT,
    match_date DATE,
    player_id int,
    plate_appearance VARCHAR,
    run_batted_in INT, --打点
    at_bat_times VARCHAR --何打席目
);

CREATE TABLE batting(
    id INT AUTO_INCREMENT,
    batting_id VARCHAR,
    batting_name VARCHAR
);

CREATE TABLE batting_contents (
    id INT AUTO_INCREMENT,
    contents_name VARCHAR, --打席の内容
    total_bases INT, --塁打
    times_on_base boolean --出塁の可否
);