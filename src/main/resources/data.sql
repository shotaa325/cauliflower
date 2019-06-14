INSERT INTO batting (batting_id, batting_name) VALUES
    ('nothing', 'なし'),
    ('single', '単打'),
    ('double', '二塁打'),
    ('triple', '三塁打'),
    ('homeRun' ,'本塁打'),
    ('bunt' , '犠打'),
    ('sacrificeFly', '犠飛'),
    ('baseOnBalls' ,'四球'),
    ('hitByPitch' ,'死球'),
    ('strikeOut' ,'三振'),
    ('others', 'それ以外');

INSERT INTO player (num, name, position, team) VALUES
    ('3', '堀切 翔太', '三塁手', 'SB高校'),
    ('5', '安田 有佑', '捕手', 'SB高校'),
    ('8', '忠平 晃樹', '右翼手', 'SB高校'),
    ('11', '笠原 優太', '遊撃手', 'SB高校'),
    ('55', '遠藤 一樹', '左翼手', 'SB高校');

INSERT INTO result (match_date, player_id, plate_appearance, run_batted_in, at_bat_times) VALUES
    ('2018-06-14', 3, '二塁打', 1, '1'),
    ('2018-06-14', 3, '三振', 0, '2'),
    ('2018-06-14', 3, '本塁打', 4, '3'),
    ('2018-06-14', 3, 'アウト', 0, '4'),
    ('2018-06-14', 3, '四球', 0, '5'),
    ('2018-06-15', 3, '三振', 0, '1'),
    ('2018-06-15', 3, '単打', 0, '2'),
    ('2018-06-15', 3, '本塁打', 2, '3'),
    ('2018-06-15', 5, '三振', 0, '1'),
    ('2018-06-15', 5, '三塁打', 2, '2'),
    ('2018-06-15', 8, '本塁打', 4, '1');

INSERT INTO batting_contents(contents_name, total_bases, times_on_base) VALUES
    ('単打', 1, 'TRUE'),
    ('二塁打', 2, 'TRUE'),
    ('三塁打', 3, 'TRUE'),
    ('本塁打', 4, 'TRUE'),
    ('四球', 0, 'TRUE'),
    ('死球', 0, 'TRUE'),
    ('三振', 0, 'FALSE'),
    ('犠打', 0, 'FALSE'),
    ('犠飛', 0, 'FALSE'),
    ('それ以外', 0, 'FALSE');