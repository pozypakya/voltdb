CREATE TABLE CATEGORY (
  CATID TINYINT DEFAULT '0' NOT NULL,
  CATNAME VARCHAR(16) DEFAULT NULL,
  CATDESCRIPTION VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (CATID)
);

CREATE TABLE ITEM (
  ITEMID INTEGER DEFAULT '0' NOT NULL,
  ITEMNAME VARCHAR(128) DEFAULT NULL,
  ITEMDESCRIPTION VARCHAR(128) DEFAULT NULL,
  SELLERID INTEGER DEFAULT '0' NOT NULL,
  CATEGORYID TINYINT DEFAULT '0' NOT NULL,
  HIGHBIDID INTEGER DEFAULT NULL,
  STARTPRICE FLOAT DEFAULT NULL,
  STARTTIME TIMESTAMP DEFAULT NULL,
  ENDTIME TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (ITEMID)
);

PARTITION TABLE ITEM ON COLUMN ITEMID;

CREATE TABLE USER (
  USERID INTEGER DEFAULT '0' NOT NULL,
  LASTNAME VARCHAR(32) DEFAULT NULL,
  FIRSTNAME VARCHAR(32) DEFAULT NULL,
  STREETADDRESS VARCHAR(32) DEFAULT NULL,
  CITY VARCHAR(16) DEFAULT NULL,
  STATE VARCHAR(2) DEFAULT NULL,
  ZIP VARCHAR(16) DEFAULT NULL,
  EMAIL VARCHAR(32) DEFAULT NULL,
  PRIMARY KEY (USERID)
);

CREATE TABLE BID (
  BIDID INTEGER DEFAULT '0' NOT NULL,
  ITEMID INTEGER DEFAULT '0' NOT NULL,
  BIDDERID INTEGER DEFAULT '0' NOT NULL,
  BIDTIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  BIDPRICE FLOAT DEFAULT NULL,
  PRIMARY KEY (BIDID, ITEMID)
);

PARTITION TABLE BID ON COLUMN ITEMID;

CREATE TABLE ITEM_EXPORT (
  ITEMID INTEGER DEFAULT '0' NOT NULL,
  ITEMNAME VARCHAR(128) DEFAULT NULL,
  ITEMDESCRIPTION VARCHAR(128) DEFAULT NULL,
  SELLERID INTEGER DEFAULT '0' NOT NULL,
  CATEGORYID TINYINT DEFAULT '0' NOT NULL,
  HIGHBIDID INTEGER DEFAULT NULL,
  STARTPRICE FLOAT DEFAULT NULL,
  STARTTIME TIMESTAMP DEFAULT NULL,
  ENDTIME TIMESTAMP DEFAULT NULL,
);

PARTITION TABLE ITEM_EXPORT ON COLUMN ITEMID;

CREATE TABLE BID_EXPORT (
  BIDID INTEGER DEFAULT '0' NOT NULL,
  ITEMID INTEGER DEFAULT '0' NOT NULL,
  BIDDERID INTEGER DEFAULT '0' NOT NULL,
  BIDTIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  BIDPRICE FLOAT DEFAULT NULL,
);

PARTITION TABLE BID_EXPORT ON COLUMN ITEMID;

LOAD CLASSES auction-procs.jar;

CREATE PROCEDURE FROM CLASS com.auctionexample.InsertIntoCategory;
CREATE PROCEDURE FROM CLASS com.auctionexample.InsertIntoUser;
CREATE PROCEDURE FROM CLASS com.auctionexample.InsertIntoItemAndBid;
CREATE PROCEDURE FROM CLASS com.auctionexample.AuctionStatus;
CREATE PROCEDURE FROM CLASS com.auctionexample.BidOnAuction;
CREATE PROCEDURE FROM CLASS com.auctionexample.GetAuctionInfo;
CREATE PROCEDURE FROM CLASS com.auctionexample.debug.SelectBids;
CREATE PROCEDURE FROM CLASS com.auctionexample.debug.SelectCategory;
CREATE PROCEDURE FROM CLASS com.auctionexample.debug.SelectItem;
CREATE PROCEDURE FROM CLASS com.auctionexample.debug.SelectUser;

EXPORT TABLE ITEM_EXPORT TO STREAM oldfile;
EXPORT TABLE BID_EXPORT TO STREAM newfile;
