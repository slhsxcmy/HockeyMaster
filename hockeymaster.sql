DROP DATABASE IF EXISTS hockeymaster;
CREATE DATABASE hockeymaster;
USE hockeymaster;

CREATE TABLE Player(
    playerID INT(11) PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(50),
    matchesWon INT(11) UNSIGNED NOT NULL, 
    matchesLost INT(11) UNSIGNED NOT NULL, 
    goalsFor INT(11) UNSIGNED NOT NULL, 
    goalsAgainst INT(11) UNSIGNED NOT NULL
);

CREATE TABLE Game(
    gameID INT(11) PRIMARY KEY AUTO_INCREMENT,
    winnerID INT(11) NOT NULL,
    loserID INT(11) NOT NULL,
    stamp TIMESTAMP NOT NULL,
    score INT(11) UNSIGNED NOT NULL,
    FOREIGN KEY (winnerID) REFERENCES Player(playerID),
    FOREIGN KEY (loserID) REFERENCES Player(playerID)
);

INSERT INTO Player (username,password,matchesWon,matchesLost,goalsFor,goalsAgainst)
    VALUES ('alice', 'alicespwd', 5, 3,  50, 36),
                    ('bob', 'bobspwd', 4, 6,  46, 75),
                    ('charlie', 'charliespwd', 3, 3,  30, 32)
    ;
    
INSERT INTO Game (winnerID,loserID,stamp,score)
    VALUES (1, 2 , CURRENT_TIMESTAMP + 1, 5),
                    (1, 3 , CURRENT_TIMESTAMP + 2, 3),
                    (2, 3 , CURRENT_TIMESTAMP + 3, 6),
                    (3, 2 , CURRENT_TIMESTAMP + 4, 3)
    ;
           