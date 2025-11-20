package com.nflweather.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service to map team names to stadium information with geocoding
 */
@Service
@Slf4j
public class StadiumMappingService {

    @Data
    public static class StadiumInfo {
        private String stadium;
        private String city;
        private String state;
        private Double latitude;
        private Double longitude;

        public StadiumInfo(String stadium, String city, String state, Double latitude, Double longitude) {
            this.stadium = stadium;
            this.city = city;
            this.state = state;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    private final Map<String, StadiumInfo> nflStadiums = new HashMap<>();
    private final Map<String, StadiumInfo> cfbStadiums = new HashMap<>();

    public StadiumMappingService() {
        initializeNFLStadiums();
        initializeCFBStadiums();
    }

    private void initializeNFLStadiums() {
        // AFC East
        nflStadiums.put("Buffalo Bills", new StadiumInfo("Highmark Stadium", "Orchard Park", "NY", 42.7738, -78.7870));
        nflStadiums.put("Miami Dolphins", new StadiumInfo("Hard Rock Stadium", "Miami Gardens", "FL", 25.9580, -80.2389));
        nflStadiums.put("New England Patriots", new StadiumInfo("Gillette Stadium", "Foxborough", "MA", 42.0909, -71.2643));
        nflStadiums.put("New York Jets", new StadiumInfo("MetLife Stadium", "East Rutherford", "NJ", 40.8128, -74.0742));

        // AFC North
        nflStadiums.put("Baltimore Ravens", new StadiumInfo("M&T Bank Stadium", "Baltimore", "MD", 39.2780, -76.6227));
        nflStadiums.put("Cincinnati Bengals", new StadiumInfo("Paycor Stadium", "Cincinnati", "OH", 39.0954, -84.5160));
        nflStadiums.put("Cleveland Browns", new StadiumInfo("Cleveland Browns Stadium", "Cleveland", "OH", 41.5061, -81.6995));
        nflStadiums.put("Pittsburgh Steelers", new StadiumInfo("Acrisure Stadium", "Pittsburgh", "PA", 40.4468, -80.0158));

        // AFC South
        nflStadiums.put("Houston Texans", new StadiumInfo("NRG Stadium", "Houston", "TX", 29.6847, -95.4107));
        nflStadiums.put("Indianapolis Colts", new StadiumInfo("Lucas Oil Stadium", "Indianapolis", "IN", 39.7601, -86.1639));
        nflStadiums.put("Jacksonville Jaguars", new StadiumInfo("TIAA Bank Field", "Jacksonville", "FL", 30.3240, -81.6373));
        nflStadiums.put("Tennessee Titans", new StadiumInfo("Nissan Stadium", "Nashville", "TN", 36.1665, -86.7713));

        // AFC West
        nflStadiums.put("Denver Broncos", new StadiumInfo("Empower Field at Mile High", "Denver", "CO", 39.7439, -105.0201));
        nflStadiums.put("Kansas City Chiefs", new StadiumInfo("GEHA Field at Arrowhead Stadium", "Kansas City", "MO", 39.0489, -94.4839));
        nflStadiums.put("Las Vegas Raiders", new StadiumInfo("Allegiant Stadium", "Las Vegas", "NV", 36.0909, -115.1833));
        nflStadiums.put("Los Angeles Chargers", new StadiumInfo("SoFi Stadium", "Inglewood", "CA", 33.9535, -118.3392));

        // NFC East
        nflStadiums.put("Dallas Cowboys", new StadiumInfo("AT&T Stadium", "Arlington", "TX", 32.7473, -97.0945));
        nflStadiums.put("New York Giants", new StadiumInfo("MetLife Stadium", "East Rutherford", "NJ", 40.8128, -74.0742));
        nflStadiums.put("Philadelphia Eagles", new StadiumInfo("Lincoln Financial Field", "Philadelphia", "PA", 39.9008, -75.1675));
        nflStadiums.put("Washington Commanders", new StadiumInfo("FedExField", "Landover", "MD", 38.9076, -76.8645));

        // NFC North
        nflStadiums.put("Chicago Bears", new StadiumInfo("Soldier Field", "Chicago", "IL", 41.8623, -87.6167));
        nflStadiums.put("Detroit Lions", new StadiumInfo("Ford Field", "Detroit", "MI", 42.3400, -83.0456));
        nflStadiums.put("Green Bay Packers", new StadiumInfo("Lambeau Field", "Green Bay", "WI", 44.5013, -88.0622));
        nflStadiums.put("Minnesota Vikings", new StadiumInfo("U.S. Bank Stadium", "Minneapolis", "MN", 44.9738, -93.2577));

        // NFC South
        nflStadiums.put("Atlanta Falcons", new StadiumInfo("Mercedes-Benz Stadium", "Atlanta", "GA", 33.7553, -84.4006));
        nflStadiums.put("Carolina Panthers", new StadiumInfo("Bank of America Stadium", "Charlotte", "NC", 35.2258, -80.8528));
        nflStadiums.put("New Orleans Saints", new StadiumInfo("Caesars Superdome", "New Orleans", "LA", 29.9511, -90.0812));
        nflStadiums.put("Tampa Bay Buccaneers", new StadiumInfo("Raymond James Stadium", "Tampa", "FL", 27.9759, -82.5033));

        // NFC West
        nflStadiums.put("Arizona Cardinals", new StadiumInfo("State Farm Stadium", "Glendale", "AZ", 33.5276, -112.2626));
        nflStadiums.put("Los Angeles Rams", new StadiumInfo("SoFi Stadium", "Inglewood", "CA", 33.9535, -118.3392));
        nflStadiums.put("San Francisco 49ers", new StadiumInfo("Levi's Stadium", "Santa Clara", "CA", 37.4032, -121.9698));
        nflStadiums.put("Seattle Seahawks", new StadiumInfo("Lumen Field", "Seattle", "WA", 47.5952, -122.3316));
    }

    private void initializeCFBStadiums() {
        // Power 5 Conferences - Top FBS Programs
        // SEC
        cfbStadiums.put("Alabama", new StadiumInfo("Bryant-Denny Stadium", "Tuscaloosa", "AL", 33.2080, -87.5502));
        cfbStadiums.put("Arkansas", new StadiumInfo("Donald W. Reynolds Razorback Stadium", "Fayetteville", "AR", 36.0678, -94.1800));
        cfbStadiums.put("Auburn", new StadiumInfo("Jordan-Hare Stadium", "Auburn", "AL", 32.6027, -85.4895));
        cfbStadiums.put("Florida", new StadiumInfo("Ben Hill Griffin Stadium", "Gainesville", "FL", 29.6500, -82.3486));
        cfbStadiums.put("Georgia", new StadiumInfo("Sanford Stadium", "Athens", "GA", 33.9496, -83.3732));
        cfbStadiums.put("Kentucky", new StadiumInfo("Kroger Field", "Lexington", "KY", 38.0219, -84.5042));
        cfbStadiums.put("LSU", new StadiumInfo("Tiger Stadium", "Baton Rouge", "LA", 30.4121, -91.1838));
        cfbStadiums.put("Ole Miss", new StadiumInfo("Vaught-Hemingway Stadium", "Oxford", "MS", 34.3646, -89.5348));
        cfbStadiums.put("Mississippi State", new StadiumInfo("Davis Wade Stadium", "Starkville", "MS", 33.4554, -88.7984));
        cfbStadiums.put("Missouri", new StadiumInfo("Faurot Field", "Columbia", "MO", 38.9356, -92.3340));
        cfbStadiums.put("South Carolina", new StadiumInfo("Williams-Brice Stadium", "Columbia", "SC", 33.9732, -81.0199));
        cfbStadiums.put("Tennessee", new StadiumInfo("Neyland Stadium", "Knoxville", "TN", 35.9550, -83.9252));
        cfbStadiums.put("Texas A&M", new StadiumInfo("Kyle Field", "College Station", "TX", 30.6100, -96.3403));
        cfbStadiums.put("Vanderbilt", new StadiumInfo("FirstBank Stadium", "Nashville", "TN", 36.1431, -86.8103));
        cfbStadiums.put("Texas", new StadiumInfo("Darrell K Royal-Texas Memorial Stadium", "Austin", "TX", 30.2840, -97.7320));
        cfbStadiums.put("Oklahoma", new StadiumInfo("Gaylord Family Oklahoma Memorial Stadium", "Norman", "OK", 35.2056, -97.4427));

        // Big Ten
        cfbStadiums.put("Illinois", new StadiumInfo("Memorial Stadium", "Champaign", "IL", 40.0995, -88.2359));
        cfbStadiums.put("Indiana", new StadiumInfo("Memorial Stadium", "Bloomington", "IN", 39.1794, -86.5228));
        cfbStadiums.put("Iowa", new StadiumInfo("Kinnick Stadium", "Iowa City", "IA", 41.6581, -91.5513));
        cfbStadiums.put("Maryland", new StadiumInfo("SECU Stadium", "College Park", "MD", 38.9908, -76.9479));
        cfbStadiums.put("Michigan", new StadiumInfo("Michigan Stadium", "Ann Arbor", "MI", 42.2658, -83.7488));
        cfbStadiums.put("Michigan State", new StadiumInfo("Spartan Stadium", "East Lansing", "MI", 42.7279, -84.4835));
        cfbStadiums.put("Minnesota", new StadiumInfo("Huntington Bank Stadium", "Minneapolis", "MN", 44.9765, -93.2246));
        cfbStadiums.put("Nebraska", new StadiumInfo("Memorial Stadium", "Lincoln", "NE", 40.8202, -96.7056));
        cfbStadiums.put("Northwestern", new StadiumInfo("Ryan Field", "Evanston", "IL", 42.0667, -87.6922));
        cfbStadiums.put("Ohio State", new StadiumInfo("Ohio Stadium", "Columbus", "OH", 40.0018, -83.0197));
        cfbStadiums.put("Penn State", new StadiumInfo("Beaver Stadium", "University Park", "PA", 40.8122, -77.8562));
        cfbStadiums.put("Purdue", new StadiumInfo("Ross-Ade Stadium", "West Lafayette", "IN", 40.4339, -86.9227));
        cfbStadiums.put("Rutgers", new StadiumInfo("SHI Stadium", "Piscataway", "NJ", 40.5140, -74.4637));
        cfbStadiums.put("Wisconsin", new StadiumInfo("Camp Randall Stadium", "Madison", "WI", 43.0700, -89.4124));

        // ACC
        cfbStadiums.put("Boston College", new StadiumInfo("Alumni Stadium", "Chestnut Hill", "MA", 42.3357, -71.1686));
        cfbStadiums.put("Clemson", new StadiumInfo("Memorial Stadium", "Clemson", "SC", 34.6780, -82.8436));
        cfbStadiums.put("Duke", new StadiumInfo("Wallace Wade Stadium", "Durham", "NC", 36.0103, -78.9403));
        cfbStadiums.put("Florida State", new StadiumInfo("Doak Campbell Stadium", "Tallahassee", "FL", 30.4380, -84.3044));
        cfbStadiums.put("Georgia Tech", new StadiumInfo("Bobby Dodd Stadium", "Atlanta", "GA", 33.7720, -84.3921));
        cfbStadiums.put("Louisville", new StadiumInfo("Cardinal Stadium", "Louisville", "KY", 38.2096, -85.7590));
        cfbStadiums.put("Miami", new StadiumInfo("Hard Rock Stadium", "Miami Gardens", "FL", 25.9580, -80.2389));
        cfbStadiums.put("NC State", new StadiumInfo("Carter-Finley Stadium", "Raleigh", "NC", 35.8007, -78.7181));
        cfbStadiums.put("North Carolina", new StadiumInfo("Kenan Memorial Stadium", "Chapel Hill", "NC", 35.9092, -79.0473));
        cfbStadiums.put("Pittsburgh", new StadiumInfo("Acrisure Stadium", "Pittsburgh", "PA", 40.4468, -80.0158));
        cfbStadiums.put("Syracuse", new StadiumInfo("JMA Wireless Dome", "Syracuse", "NY", 43.0361, -76.1364));
        cfbStadiums.put("Virginia", new StadiumInfo("Scott Stadium", "Charlottesville", "VA", 38.0312, -78.5190));
        cfbStadiums.put("Virginia Tech", new StadiumInfo("Lane Stadium", "Blacksburg", "VA", 37.2207, -80.4183));
        cfbStadiums.put("Wake Forest", new StadiumInfo("Truist Field", "Winston-Salem", "NC", 36.1023, -80.2449));

        // Big 12
        cfbStadiums.put("Baylor", new StadiumInfo("McLane Stadium", "Waco", "TX", 31.5589, -97.1119));
        cfbStadiums.put("Iowa State", new StadiumInfo("Jack Trice Stadium", "Ames", "IA", 42.0140, -93.6357));
        cfbStadiums.put("Kansas", new StadiumInfo("David Booth Kansas Memorial Stadium", "Lawrence", "KS", 38.9592, -95.2517));
        cfbStadiums.put("Kansas State", new StadiumInfo("Bill Snyder Family Stadium", "Manhattan", "KS", 39.2011, -96.5925));
        cfbStadiums.put("Oklahoma State", new StadiumInfo("Boone Pickens Stadium", "Stillwater", "OK", 36.1282, -97.0662));
        cfbStadiums.put("TCU", new StadiumInfo("Amon G. Carter Stadium", "Fort Worth", "TX", 32.7096, -97.3646));
        cfbStadiums.put("Texas Tech", new StadiumInfo("Jones AT&T Stadium", "Lubbock", "TX", 33.5906, -101.8725));
        cfbStadiums.put("West Virginia", new StadiumInfo("Milan Puskar Stadium", "Morgantown", "WV", 39.6508, -79.9553));

        // Pac-12 (now mostly in Big Ten/Big 12)
        cfbStadiums.put("Arizona", new StadiumInfo("Arizona Stadium", "Tucson", "AZ", 32.2290, -110.9490));
        cfbStadiums.put("Arizona State", new StadiumInfo("Mountain America Stadium", "Tempe", "AZ", 33.4255, -111.9330));
        cfbStadiums.put("California", new StadiumInfo("California Memorial Stadium", "Berkeley", "CA", 37.8702, -122.2508));
        cfbStadiums.put("Colorado", new StadiumInfo("Folsom Field", "Boulder", "CO", 40.0095, -105.2661));
        cfbStadiums.put("Oregon", new StadiumInfo("Autzen Stadium", "Eugene", "OR", 44.0584, -123.0681));
        cfbStadiums.put("Oregon State", new StadiumInfo("Reser Stadium", "Corvallis", "OR", 44.5663, -123.2786));
        cfbStadiums.put("Stanford", new StadiumInfo("Stanford Stadium", "Stanford", "CA", 37.4347, -122.1611));
        cfbStadiums.put("UCLA", new StadiumInfo("Rose Bowl", "Pasadena", "CA", 34.1611, -118.1676));
        cfbStadiums.put("USC", new StadiumInfo("Los Angeles Memorial Coliseum", "Los Angeles", "CA", 34.0141, -118.2879));
        cfbStadiums.put("Utah", new StadiumInfo("Rice-Eccles Stadium", "Salt Lake City", "UT", 40.7597, -111.8491));
        cfbStadiums.put("Washington", new StadiumInfo("Husky Stadium", "Seattle", "WA", 47.6503, -122.3017));
        cfbStadiums.put("Washington State", new StadiumInfo("Gesa Field at Martin Stadium", "Pullman", "WA", 46.7319, -117.1657));

        // Group of 5 - Selected major programs
        cfbStadiums.put("UCF", new StadiumInfo("FBC Mortgage Stadium", "Orlando", "FL", 28.6074, -81.1965));
        cfbStadiums.put("Cincinnati", new StadiumInfo("Nippert Stadium", "Cincinnati", "OH", 39.1320, -84.5148));
        cfbStadiums.put("Houston", new StadiumInfo("TDECU Stadium", "Houston", "TX", 29.7209, -95.3461));
        cfbStadiums.put("Memphis", new StadiumInfo("Simmons Bank Liberty Stadium", "Memphis", "TN", 35.1214, -89.9947));
        cfbStadiums.put("BYU", new StadiumInfo("LaVell Edwards Stadium", "Provo", "UT", 40.2578, -111.6563));
        cfbStadiums.put("Boise State", new StadiumInfo("Albertsons Stadium", "Boise", "ID", 43.6045, -116.1977));
    }

    public StadiumInfo getNFLStadiumInfo(String teamName) {
        StadiumInfo info = nflStadiums.get(teamName);
        if (info == null) {
            log.warn("No stadium information found for NFL team: {}", teamName);
        }
        return info;
    }

    public StadiumInfo getCFBStadiumInfo(String teamName) {
        // First try exact match
        StadiumInfo info = cfbStadiums.get(teamName);
        
        // If no exact match, try to find by removing mascot name
        if (info == null) {
            // Common CFB mascot patterns from Odds API
            String normalizedName = teamName
                .replace(" Crimson Tide", "")
                .replace(" Razorbacks", "")
                .replace(" Tigers", "")
                .replace(" Gators", "")
                .replace(" Bulldogs", "")
                .replace(" Wildcats", "")
                .replace(" Rebels", "")
                .replace(" Commodores", "")
                .replace(" Volunteers", "")
                .replace(" Aggies", "")
                .replace(" Longhorns", "")
                .replace(" Sooners", "")
                .replace(" Fighting Irish", "")
                .replace(" Ducks", "")
                .replace(" Trojans", "")
                .replace(" Bruins", "")
                .replace(" Utes", "")
                .replace(" Huskies", "")
                .replace(" Cougars", "")
                .replace(" Sun Devils", "")
                .replace(" Buffaloes", "")
                .replace(" Cardinal", "")
                .replace(" Golden Bears", "")
                .replace(" Beavers", "")
                .replace(" Cougars", "")
                .replace(" Hawkeyes", "")
                .replace(" Hoosiers", "")
                .replace(" Spartans", "")
                .replace(" Wolverines", "")
                .replace(" Golden Gophers", "")
                .replace(" Cornhuskers", "")
                .replace(" Nittany Lions", "")
                .replace(" Buckeyes", "")
                .replace(" Boilermakers", "")
                .replace(" Scarlet Knights", "")
                .replace(" Badgers", "")
                .replace(" Terrapins", "")
                .replace(" Eagles", "")
                .replace(" Yellow Jackets", "")
                .replace(" Cardinals", "")
                .replace(" Hurricanes", "")
                .replace(" Tar Heels", "")
                .replace(" Blue Devils", "")
                .replace(" Seminoles", "")
                .replace(" Panthers", "")
                .replace(" Orange", "")
                .replace(" Hokies", "")
                .replace(" Cavaliers", "")
                .replace(" Demon Deacons", "")
                .replace(" Bears", "")
                .replace(" Cyclones", "")
                .replace(" Jayhawks", "")
                .replace(" Cowboys", "")
                .replace(" Horned Frogs", "")
                .replace(" Red Raiders", "")
                .replace(" Mountaineers", "")
                .replace(" Knights", "")
                .replace(" Bearcats", "")
                .replace(" Roadrunners", "")
                .replace(" Owls", "")
                .replace(" Bobcats", "")
                .replace(" Miners", "")
                .replace(" Blazers", "")
                .replace(" Bulldogs", "")
                .replace(" Blue Raiders", "")
                .replace(" Rockets", "")
                .replace(" Jaguars", "")
                .replace(" Gamecocks", "")
                .replace(" Dukes", "")
                .replace(" Black Knights", "")
                .replace(" Falcons", "")
                .replace(" Broncos", "")
                .replace(" Mustangs", "")
                .replace(" Aztecs", "")
                .replace(" Rams", "")
                .replace(" Wolf Pack", "")
                .replace(" Wolfpack", "")
                .trim();
            
            info = cfbStadiums.get(normalizedName);
        }
        
        if (info == null) {
            log.warn("No stadium information found for CFB team: {} (tried normalized: {})", teamName, teamName.replaceAll(" (Crimson Tide|Razorbacks|Tigers|Gators|Bulldogs|Wildcats|Rebels|Commodores|Volunteers|Aggies|Longhorns|Sooners|Fighting Irish|Ducks|Trojans|Bruins|Utes|Huskies|Cougars|Sun Devils|Buffaloes|Cardinal|Golden Bears|Beavers|Hawkeyes|Hoosiers|Spartans|Wolverines|Golden Gophers|Cornhuskers|Nittany Lions|Buckeyes|Boilermakers|Scarlet Knights|Badgers|Terrapins|Eagles|Yellow Jackets|Cardinals|Hurricanes|Tar Heels|Blue Devils|Seminoles|Panthers|Orange|Hokies|Cavaliers|Demon Deacons|Bears|Cyclones|Jayhawks|Cowboys|Horned Frogs|Red Raiders|Mountaineers|Knights|Bearcats|Roadrunners|Owls|Bobcats|Miners|Blazers|Blue Raiders|Rockets|Jaguars|Gamecocks|Dukes|Black Knights|Falcons|Broncos|Mustangs|Aztecs|Rams|Wolf Pack|Wolfpack)$", "").trim());
        }
        return info;
    }
}
