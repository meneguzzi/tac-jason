// TAC Agent
// Using the following predicates to express need:
//requires(CAT,TYPE,DAY,AMOUNT)


+gameStarted(G) : not gameStarted(G)
 <- +gameStarted(G);
    !setAuctionInfo(0);
    .print("Game ", G, " started").

+gameStopped(G) : true
 <- -gameStarted(G);
    .print("Game ", G, "stopped").

+!setAuctionInfo(Auction) : Auction < 28
 <- !getAuctionInfo(Auction);
    !setAuctionInfo(Auction+1).

+!setAuctionInfo(Auction) : true
 <- true.
//Auction information startup
+!getAuctionInfo(Auction) : true
<- getAuctionCategory(Auction, Cat);
   getAuctionType(Auction, Type);
   getAuctionDay(Auction, Day);
   +auction(Auction, Cat, Type, Day).

//Transaction Information
+transaction(Auction, Amount, Price) : true
 <- !updateRequirements(Auction, Amount, Price);
    .print(transaction(Auction, Amount, Price)).

//Quote monitoring beliefs
+auctionClosed(Auction) : auction(Auction, Cat, Type, Day)
 <- -auction(Auction, Cat, Type, Day);
    //!removeMonitoredAuction(Auction);
    .print("Auction ", Auction, " closed").

+quoteUpdated(Auction, AskPrice, BidPrice) : quote(Auction, OldAskPrice, OldBidPrice)
 <- -quote(Auction, OldAskPrice, OldBidPrice);
    +quote(Auction, AskPrice, BidPrice);
    !monitorAuction(Auction);
    //!checkMonitoredAuctions(Auction, AskPrice, BidPrice);
    true.//.print("Quote update on ", quote(Auction, AskPrice, BidPrice)).

+quoteUpdated(Auction, AskPrice, BidPrice) : true
 <- +quote(Auction, AskPrice, BidPrice);
    !monitorAuction(Auction);
    //!checkMonitoredAuctions(Auction, AskPrice, BidPrice);
    true.//.print("Quote update on ", quote(Auction, AskPrice, BidPrice)).

+bidRejected(Auction, Reason) : true
 <- .print("Bid rejected for auction ", Auction, ": ", Reason).


//Start up beliefs for monitoring auctions for the agent

+preference(Customer, InFlight, OutFlight, 
            HotelPremium, AW, AP, MU) : true //Customer = "client1"
  <- //.print("Finding auctions for ", Customer);
     !findAuctions(Customer).
     //!processRequirements(Customer).

     /*
+!processRequirements(Customer) : preference(Customer, InFlight, OutFlight, 
                                      HotelPremium, AW, AP, MU)
  <- //.print("Processing requirements for '",Customer,"'");
     !addRequirement("cat_flight","type_inflight",InFlight,1);
     !addRequirement("cat_flight","type_outflight",OutFlight,1);
     !addRequirementRange("cat_hotel","type_good_hotel",InFlight,OutFlight);
     //Let's also go for a cheap-o
     !addRequirementRange("cat_hotel","type_cheap_hotel",InFlight,OutFlight);
     true.//.print("Done").

+!processEntertainment(Customer) :preference(Customer, InFlight, OutFlight, 
                                      HotelPremium, AW, AP, MU)
  <-

+!addEntertainment
*/

//Plans for dealing with requirement quantities---------------------------------

+!addRequirementRange(Cat,Type,FromDay,ToDay) : FromDay = ToDay
 <- true.

+!addRequirementRange(Cat,Type,FromDay,ToDay) : FromDay < ToDay
 <- !addRequirement(Cat,Type,FromDay,1);
    !addRequirementRange(Cat,Type,FromDay+1,ToDay).

+!addRequirement(Cat,Type,Day,Amount) : requires(Cat,Type,Day,OldAmount)
 <- -requires(Cat,Type,Day,OldAmount);
    +requires(Cat,Type,Day,Amount+OldAmount);
    true.//.print("Adding requirement for: ",requires(Cat,Type,Day,Amount+OldAmount)).
  
+!addRequirement(Cat,Type,Day,Amount) : true
 <- +requires(Cat,Type,Day,Amount);
    true.//.print("Adding requirement for: ",requires(Cat,Type,Day,Amount)).

+!removeRequirement(Cat,Type,Day,Amount) : requires(Cat,Type,Day,OldAmount) & Amount > OldAmount
 <- -requires(Cat,Type,Day,OldAmount);
    true.//.print("Removing requirement for: ",requires(Cat,Type,Day,Amount)).
 
+!removeRequirement(Cat,Type,Day,Amount) : requires(Cat,Type,Day,OldAmount)
 <- -requires(Cat,Type,Day,OldAmount);
    +requires(Cat,Type,Day,OldAmount-Amount);
    true.//.print("Removing requirement for: ",requires(Cat,Type,Day,Amount)).
    
//------------------------------------------------------------------------------

//Bidding strategy for flights is to simply place the bid
+!monitorAuction(Auction) : auction(Auction, "cat_flight", Type, Day) & 
			    requires("cat_flight", Type, Day, Amount) & Amount > 0
  <- ?quote(Auction, AskPrice, BidPrice);
     placeBid(Auction,Amount,AskPrice+1);
     .print("Placing bid on Auction ", Auction, " for ", Amount, " items at $", AskPrice+1).

//For good hotel rooms we keep on bidding until they close
+!monitorAuction(Auction) : auction(Auction, "cat_hotel", "type_good_hotel", Day) & 
			    requires("cat_hotel", "type_good_hotel", Day, Amount) & Amount > 0
  <- ?quote(Auction, AskPrice, BidPrice);
     placeBid(Auction,Amount,AskPrice+50);
     .print("Placing bid on Auction ", Auction, " for ", Amount, " items at $", AskPrice+1).

//For good hotel rooms we keep on bidding until they close
+!monitorAuction(Auction) : auction(Auction, "cat_hotel", "type_cheap_hotel", Day) & 
			    requires("cat_hotel", "type_cheap_hotel", Day, Amount) & Amount > 0
  <- ?quote(Auction, AskPrice, BidPrice);
     placeBid(Auction,Amount,AskPrice+50);
     .print("Placing bid on Auction ", Auction, " for ", Amount, " items at $", AskPrice+1).
     
+!monitorAuction(Auction) : true
  <- true.//.print("Auction ", Auction, " is not relevant").


+!updateRequirements(Auction, Amount, Price) : auction(Auction, Cat, Type, Day) &
                                               requires(Cat, Type, Day, ReqAmount)
  <- !removeRequirement(Cat, Type, Day, Amount).

+!updateRequirements(Auction, Amount, Price) : true
  <- .print("This update should not have happened").

+!findAuctions(Customer) : preference(Customer, InFlight, OutFlight, 
                                      HotelPremium, AW, AP, MU)
	<- !findFlightAuctions(InFlight,OutFlight);
	   !findHotelAuctions(InFlight,OutFlight).

+!findFlightAuctions(InFlight,OutFlight) : true
  <-  getAuctionFor("cat_flight", "type_inflight", InFlight, AucInFlight);
     .print("Inflight auction is ", AucInFlight);
     .wait("+quoteUpdated(0,A,B)");
     ?quote(AucInFlight, AP1, BP1);
     +monitorAuction(AucInFlight, 1, AP1+1);
     .print("Monitoring auction for in flight: ", AucInFlight);
      getAuctionFor("cat_flight", "type_outflight", OutFlight, AucOutFlight);
     ?quote(AucOutFlight, AP2, BP2);
     +monitorAuction(AucOutFlight, 1, AP2+1);
     .print("Monitoring auction for out flight: ", AucOutFlight).

+!findHotelAuctions(Day,LastDay) : Day < LastDay
   <-  getAuctionFor("cat_hotel", "type_good_hotel", Day, AucHotel);
      ?quote(AucHotel, AP, BP);
      +monitorAuction(AucHotel, 1, AP+1);
      .print("Monitoring auction for hotel at day ", Day, ": ", AucHotel);
      !findHotelAuctions(Day+1,LastDay).

+!findHotelAuctions(Day,LastDay) : Day = LastDay
   <- .print("Done finding hotels").
   
//React to updates on auctions that are being monitored

/*
+monitorAuction(Auction, Amount, Price) : quote(Auction, AskPrice, BidPrice) & AskPrice > Price
 <- -monitorAuction(Auction, Amount, Price);
     placeBid(Auction, Amount, AskPrice+1);
    +monitorAuction(Auction, Amount, AskPrice+1);
    .print("Placing bid on Auction ", Auction, " for ", 1, " item at $", AskPrice+1).
*/

/*+quote(Auction, AskPrice, BidPrice) : monitorAuction(Auction, Amount, Price)
 <-  placeBid(Auction, 1, AskPrice+1);
    .print("Placing bid on Auction ", Auction, " for ", 1, " item at $", AskPrice+1).*/
/*
+!checkMonitoredAuctions(Auction, AskPrice, BidPrice) : monitorAuction(Auction, Amount, Price)
 <-  placeBid(Auction, 1, AskPrice+1);
    .print("Placing bid on Auction ", Auction, " for ", 1, " item at $", AskPrice+1).

+!checkMonitoredAuctions(Auction, AskPrice, BidPrice) : true
 <- .print("Agent is not monitoring auction ", Auction).

+!removeMonitoredAuction(Auction) : monitorAuction(Auction, Amount, Price)
 <- -monitorAuction(Auction, Amount, Price);
    .print("Agent is no longer monitoring auction ", Auction).
*/

/*+quote(Auction, AskPrice, BidPrice) : Auction = 1
  <- !testActions.*/

/*
+!testActions : true
 <- .print("Testing Actions");
     getAuctionDay(1, Day);
    .print(auctionDay(1, Day));
     getAuctionCategory(1, Cat);
    .print(auctionCategory(1, Cat));
     getAuctionType(1, Type);
    .print(auctionType(1, Type));
     getAuctionFor(Cat, Type, Day, Auction);
    .print(auctionFor(Cat, Type, Day, Auction)).*/