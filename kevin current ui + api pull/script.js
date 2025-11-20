// API Key
const ODDS_API_KEY = 'f9d2d5523af245e8f1b47c914e3bbae6';

// Stadium locations for NFL and CFB teams
const stadiumLocations = {
  // NFL Teams
  'Arizona Cardinals': 'Glendale, AZ',
  'Atlanta Falcons': 'Atlanta, GA',
  'Baltimore Ravens': 'Baltimore, MD',
  'Buffalo Bills': 'Orchard Park, NY',
  'Carolina Panthers': 'Charlotte, NC',
  'Chicago Bears': 'Chicago, IL',
  'Cincinnati Bengals': 'Cincinnati, OH',
  'Cleveland Browns': 'Cleveland, OH',
  'Dallas Cowboys': 'Arlington, TX',
  'Denver Broncos': 'Denver, CO',
  'Detroit Lions': 'Detroit, MI',
  'Green Bay Packers': 'Green Bay, WI',
  'Houston Texans': 'Houston, TX',
  'Indianapolis Colts': 'Indianapolis, IN',
  'Jacksonville Jaguars': 'Jacksonville, FL',
  'Kansas City Chiefs': 'Kansas City, MO',
  'Las Vegas Raiders': 'Las Vegas, NV',
  'Los Angeles Chargers': 'Inglewood, CA',
  'Los Angeles Rams': 'Inglewood, CA',
  'Miami Dolphins': 'Miami Gardens, FL',
  'Minnesota Vikings': 'Minneapolis, MN',
  'New England Patriots': 'Foxborough, MA',
  'New Orleans Saints': 'New Orleans, LA',
  'New York Giants': 'East Rutherford, NJ',
  'New York Jets': 'East Rutherford, NJ',
  'Philadelphia Eagles': 'Philadelphia, PA',
  'Pittsburgh Steelers': 'Pittsburgh, PA',
  'San Francisco 49ers': 'Santa Clara, CA',
  'Seattle Seahawks': 'Seattle, WA',
  'Tampa Bay Buccaneers': 'Tampa, FL',
  'Tennessee Titans': 'Nashville, TN',
  'Washington Commanders': 'Landover, MD',
  
  // CFB Teams - ALL FBS
  'Air Force': 'Colorado Springs, CO',
  'Akron': 'Akron, OH',
  'Alabama': 'Tuscaloosa, AL',
  'Appalachian State': 'Boone, NC',
  'Arizona': 'Tucson, AZ',
  'Arizona State': 'Tempe, AZ',
  'Arkansas': 'Fayetteville, AR',
  'Arkansas State': 'Jonesboro, AR',
  'Army': 'West Point, NY',
  'Auburn': 'Auburn, AL',
  'Ball State': 'Muncie, IN',
  'Baylor': 'Waco, TX',
  'Boise State': 'Boise, ID',
  'Boston College': 'Chestnut Hill, MA',
  'Bowling Green': 'Bowling Green, OH',
  'Buffalo': 'Buffalo, NY',
  'BYU': 'Provo, UT',
  'California': 'Berkeley, CA',
  'Central Michigan': 'Mount Pleasant, MI',
  'Charlotte': 'Charlotte, NC',
  'Cincinnati': 'Cincinnati, OH',
  'Clemson': 'Clemson, SC',
  'Coastal Carolina': 'Conway, SC',
  'Colorado': 'Boulder, CO',
  'Colorado State': 'Fort Collins, CO',
  'Duke': 'Durham, NC',
  'East Carolina': 'Greenville, NC',
  'Eastern Michigan': 'Ypsilanti, MI',
  'Florida': 'Gainesville, FL',
  'Florida Atlantic': 'Boca Raton, FL',
  'Florida International': 'Miami, FL',
  'Florida State': 'Tallahassee, FL',
  'Fresno State': 'Fresno, CA',
  'Georgia': 'Athens, GA',
  'Georgia Southern': 'Statesboro, GA',
  'Georgia State': 'Atlanta, GA',
  'Georgia Tech': 'Atlanta, GA',
  'Hawaii': 'Honolulu, HI',
  'Houston': 'Houston, TX',
  'Illinois': 'Champaign, IL',
  'Indiana': 'Bloomington, IN',
  'Iowa': 'Iowa City, IA',
  'Iowa State': 'Ames, IA',
  'James Madison': 'Harrisonburg, VA',
  'Kansas': 'Lawrence, KS',
  'Kansas State': 'Manhattan, KS',
  'Kent State': 'Kent, OH',
  'Kentucky': 'Lexington, KY',
  'Liberty': 'Lynchburg, VA',
  'Louisiana': 'Lafayette, LA',
  'Louisiana Monroe': 'Monroe, LA',
  'Louisiana Tech': 'Ruston, LA',
  'Louisville': 'Louisville, KY',
  'LSU': 'Baton Rouge, LA',
  'Marshall': 'Huntington, WV',
  'Maryland': 'College Park, MD',
  'Memphis': 'Memphis, TN',
  'Miami': 'Miami Gardens, FL',
  'Miami (OH)': 'Oxford, OH',
  'Michigan': 'Ann Arbor, MI',
  'Michigan State': 'East Lansing, MI',
  'Middle Tennessee': 'Murfreesboro, TN',
  'Minnesota': 'Minneapolis, MN',
  'Mississippi State': 'Starkville, MS',
  'Missouri': 'Columbia, MO',
  'Navy': 'Annapolis, MD',
  'NC State': 'Raleigh, NC',
  'Nebraska': 'Lincoln, NE',
  'Nevada': 'Reno, NV',
  'New Mexico': 'Albuquerque, NM',
  'New Mexico State': 'Las Cruces, NM',
  'North Carolina': 'Chapel Hill, NC',
  'North Texas': 'Denton, TX',
  'Northern Illinois': 'DeKalb, IL',
  'Northwestern': 'Evanston, IL',
  'Notre Dame': 'South Bend, IN',
  'Ohio': 'Athens, OH',
  'Ohio State': 'Columbus, OH',
  'Oklahoma': 'Norman, OK',
  'Oklahoma State': 'Stillwater, OK',
  'Old Dominion': 'Norfolk, VA',
  'Ole Miss': 'Oxford, MS',
  'Oregon': 'Eugene, OR',
  'Oregon State': 'Corvallis, OR',
  'Penn State': 'State College, PA',
  'Pittsburgh': 'Pittsburgh, PA',
  'Purdue': 'West Lafayette, IN',
  'Rice': 'Houston, TX',
  'Rutgers': 'Piscataway, NJ',
  'San Diego State': 'San Diego, CA',
  'San Jose State': 'San Jose, CA',
  'SMU': 'Dallas, TX',
  'South Alabama': 'Mobile, AL',
  'South Carolina': 'Columbia, SC',
  'South Florida': 'Tampa, FL',
  'Southern Mississippi': 'Hattiesburg, MS',
  'Stanford': 'Stanford, CA',
  'Syracuse': 'Syracuse, NY',
  'TCU': 'Fort Worth, TX',
  'Temple': 'Philadelphia, PA',
  'Tennessee': 'Knoxville, TN',
  'Texas': 'Austin, TX',
  'Texas A&M': 'College Station, TX',
  'Texas State': 'San Marcos, TX',
  'Texas Tech': 'Lubbock, TX',
  'Toledo': 'Toledo, OH',
  'Troy': 'Troy, AL',
  'Tulane': 'New Orleans, LA',
  'Tulsa': 'Tulsa, OK',
  'UAB': 'Birmingham, AL',
  'UCF': 'Orlando, FL',
  'UCLA': 'Pasadena, CA',
  'UConn': 'East Hartford, CT',
  'UMass': 'Amherst, MA',
  'UNLV': 'Las Vegas, NV',
  'USC': 'Los Angeles, CA',
  'Utah': 'Salt Lake City, UT',
  'Utah State': 'Logan, UT',
  'UTEP': 'El Paso, TX',
  'UTSA': 'San Antonio, TX',
  'Vanderbilt': 'Nashville, TN',
  'Virginia': 'Charlottesville, VA',
  'Virginia Tech': 'Blacksburg, VA',
  'Wake Forest': 'Winston-Salem, NC',
  'Washington': 'Seattle, WA',
  'Washington State': 'Pullman, WA',
  'West Virginia': 'Morgantown, WV',
  'Western Kentucky': 'Bowling Green, KY',
  'Western Michigan': 'Kalamazoo, MI',
  'Wisconsin': 'Madison, WI',
  'Wyoming': 'Laramie, WY',
};

// Team aliases - maps alternative names to official team names
const teamAliases = {
  // CFB Common Aliases
  'UT': 'Texas',
  'Texas Longhorns': 'Texas',
  'Alabama Crimson Tide': 'Alabama',
  'Ohio State Buckeyes': 'Ohio State',
  'Georgia Bulldogs': 'Georgia',
  'UGA': 'Georgia',
  'Michigan Wolverines': 'Michigan',
  'Oklahoma Sooners': 'Oklahoma',
  'OU': 'Oklahoma',
  'USC Trojans': 'USC',
  'Penn State Nittany Lions': 'Penn State',
  'PSU': 'Penn State',
  'LSU Tigers': 'LSU',
  'Florida Gators': 'Florida',
  'UF': 'Florida',
  'Notre Dame Fighting Irish': 'Notre Dame',
  'Clemson Tigers': 'Clemson',
  'Texas A&M Aggies': 'Texas A&M',
  'TAMU': 'Texas A&M',
  'Auburn Tigers': 'Auburn',
  'Oregon Ducks': 'Oregon',
  'UO': 'Oregon',
  'Florida State Seminoles': 'Florida State',
  'FSU': 'Florida State',
  'Tennessee Volunteers': 'Tennessee',
  'Mississippi': 'Ole Miss',
  'Ole Miss Rebels': 'Ole Miss',
  'Mississippi State Bulldogs': 'Mississippi State',
  'Arkansas Razorbacks': 'Arkansas',
  'Kentucky Wildcats': 'Kentucky',
  'South Carolina Gamecocks': 'South Carolina',
  'Missouri Tigers': 'Missouri',
  'Mizzou': 'Missouri',
  'Vanderbilt Commodores': 'Vanderbilt',
  'TCU Horned Frogs': 'TCU',
  'Baylor Bears': 'Baylor',
  'Oklahoma State Cowboys': 'Oklahoma State',
  'Kansas State Wildcats': 'Kansas State',
  'KSU': 'Kansas State',
  'Kansas Jayhawks': 'Kansas',
  'KU': 'Kansas',
  'Iowa State Cyclones': 'Iowa State',
  'ISU': 'Iowa State',
  'West Virginia Mountaineers': 'West Virginia',
  'WVU': 'West Virginia',
  'Iowa Hawkeyes': 'Iowa',
  'Wisconsin Badgers': 'Wisconsin',
  'Nebraska Cornhuskers': 'Nebraska',
  'Minnesota Golden Gophers': 'Minnesota',
  'Northwestern Wildcats': 'Northwestern',
  'Purdue Boilermakers': 'Purdue',
  'Illinois Fighting Illini': 'Illinois',
  'Indiana Hoosiers': 'Indiana',
  'IU': 'Indiana',
  'Rutgers Scarlet Knights': 'Rutgers',
  'Maryland Terrapins': 'Maryland',
  'UMD': 'Maryland',
  'Michigan State Spartans': 'Michigan State',
  'MSU': 'Michigan State',
  'Washington Huskies': 'Washington',
  'UW': 'Washington',
  'UCLA Bruins': 'UCLA',
  'Stanford Cardinal': 'Stanford',
  'California Golden Bears': 'California',
  'Cal': 'California',
  'Arizona Wildcats': 'Arizona',
  'UA': 'Arizona',
  'Arizona State Sun Devils': 'Arizona State',
  'ASU': 'Arizona State',
  'Colorado Buffaloes': 'Colorado',
  'CU': 'Colorado',
  'Utah Utes': 'Utah',
  'Oregon State Beavers': 'Oregon State',
  'OSU': 'Oregon State',
  'Washington State Cougars': 'Washington State',
  'WSU': 'Washington State',
  'NC State Wolfpack': 'NC State',
  'NCSU': 'NC State',
  'North Carolina Tar Heels': 'North Carolina',
  'UNC': 'North Carolina',
  'Duke Blue Devils': 'Duke',
  'Virginia Cavaliers': 'Virginia',
  'UVA': 'Virginia',
  'Virginia Tech Hokies': 'Virginia Tech',
  'VT': 'Virginia Tech',
  'Miami Hurricanes': 'Miami',
  'The U': 'Miami',
  'UM': 'Miami',
  'Georgia Tech Yellow Jackets': 'Georgia Tech',
  'GT': 'Georgia Tech',
  'Louisville Cardinals': 'Louisville',
  'Pittsburgh Panthers': 'Pittsburgh',
  'Pitt': 'Pittsburgh',
  'Syracuse Orange': 'Syracuse',
  'Boston College Eagles': 'Boston College',
  'BC': 'Boston College',
  'Wake Forest Demon Deacons': 'Wake Forest',
  'SMU Mustangs': 'SMU',
  'Tulane Green Wave': 'Tulane',
  'Memphis Tigers': 'Memphis',
  'Navy Midshipmen': 'Navy',
  'Army Black Knights': 'Army',
  'Air Force Falcons': 'Air Force',
  'BYU Cougars': 'BYU',
  'UCF Knights': 'UCF',
  'Cincinnati Bearcats': 'Cincinnati',
  'Houston Cougars': 'Houston',
  'UH': 'Houston',
  'Boise State Broncos': 'Boise State',
  'San Diego State Aztecs': 'San Diego State',
  'SDSU': 'San Diego State',
  'Fresno State Bulldogs': 'Fresno State',
  'Hawaii Rainbow Warriors': 'Hawaii',
  'Hawai\'i Rainbow Warriors': 'Hawaii',
  'Texas Tech Red Raiders': 'Texas Tech',
  'TTU': 'Texas Tech',
  'UTSA Roadrunners': 'UTSA',
  'UTEP Miners': 'UTEP',
  'Texas State Bobcats': 'Texas State',
  'North Texas Mean Green': 'North Texas',
  'UNT': 'North Texas',
  'Rice Owls': 'Rice',
  'JMU': 'James Madison',
  'James Madison Dukes': 'James Madison',
  'App State': 'Appalachian State',
  'Appalachian State Mountaineers': 'Appalachian State',
  'Coastal Carolina Chanticleers': 'Coastal Carolina',
  'Louisiana Ragin Cajuns': 'Louisiana',
  'ULL': 'Louisiana',
  'Louisiana Lafayette': 'Louisiana',
  'ULM': 'Louisiana Monroe',
  'Louisiana Monroe Warhawks': 'Louisiana Monroe',
  'Louisiana Tech Bulldogs': 'Louisiana Tech',
  'Southern Miss': 'Southern Mississippi',
  'Southern Miss Golden Eagles': 'Southern Mississippi',
  'USM': 'Southern Mississippi',
  'South Alabama Jaguars': 'South Alabama',
  'USA': 'South Alabama',
  'South Florida Bulls': 'South Florida',
  'USF': 'South Florida',
  'FAU': 'Florida Atlantic',
  'Florida Atlantic Owls': 'Florida Atlantic',
  'FIU': 'Florida International',
  'Florida International Panthers': 'Florida International',
  'East Carolina Pirates': 'East Carolina',
  'ECU': 'East Carolina',
  'Marshall Thundering Herd': 'Marshall',
  'Old Dominion Monarchs': 'Old Dominion',
  'ODU': 'Old Dominion',
  'Charlotte 49ers': 'Charlotte',
  'Georgia Southern Eagles': 'Georgia Southern',
  'Georgia State Panthers': 'Georgia State',
  'Troy Trojans': 'Troy',
  'UAB Blazers': 'UAB',
  'Middle Tennessee Blue Raiders': 'Middle Tennessee',
  'MTSU': 'Middle Tennessee',
  'Western Kentucky Hilltoppers': 'Western Kentucky',
  'WKU': 'Western Kentucky',
  'UMass Minutemen': 'UMass',
  'UConn Huskies': 'UConn',
  'Connecticut': 'UConn',
  'Liberty Flames': 'Liberty',
  'Akron Zips': 'Akron',
  'Ball State Cardinals': 'Ball State',
  'Bowling Green Falcons': 'Bowling Green',
  'BGSU': 'Bowling Green',
  'Buffalo Bulls': 'Buffalo',
  'Central Michigan Chippewas': 'Central Michigan',
  'CMU': 'Central Michigan',
  'Eastern Michigan Eagles': 'Eastern Michigan',
  'EMU': 'Eastern Michigan',
  'Kent State Golden Flashes': 'Kent State',
  'Miami RedHawks': 'Miami (OH)',
  'Miami OH': 'Miami (OH)',
  'Northern Illinois Huskies': 'Northern Illinois',
  'NIU': 'Northern Illinois',
  'Ohio Bobcats': 'Ohio',
  'Toledo Rockets': 'Toledo',
  'Western Michigan Broncos': 'Western Michigan',
  'WMU': 'Western Michigan',
  'Colorado State Rams': 'Colorado State',
  'CSU': 'Colorado State',
  'Nevada Wolf Pack': 'Nevada',
  'New Mexico Lobos': 'New Mexico',
  'UNM': 'New Mexico',
  'New Mexico State Aggies': 'New Mexico State',
  'NMSU': 'New Mexico State',
  'San Jose State Spartans': 'San Jose State',
  'SJSU': 'San Jose State',
  'UNLV Rebels': 'UNLV',
  'Utah State Aggies': 'Utah State',
  'USU': 'Utah State',
  'Wyoming Cowboys': 'Wyoming',
  'Arkansas State Red Wolves': 'Arkansas State',
  'A-State': 'Arkansas State',
  'Temple Owls': 'Temple',
  'Tulsa Golden Hurricane': 'Tulsa',
};

// Check if team exists in our database + alias
function isKnownTeam(teamName) {
  // Check alias first
  if (teamAliases[teamName]) {
    return true;
  }
  
  // Exact match
  if (stadiumLocations[teamName]) {
    return true;
  }
  
  // Partial match
  for (const team of Object.keys(stadiumLocations)) {
    if (teamName.includes(team) || team.includes(teamName)) {
      return true;
    }
  }
  
  return false;
}

// Get stadium location for a team + alias
function getStadiumLocation(teamName) {
  // Check alias first
  if (teamAliases[teamName]) {
    const officialName = teamAliases[teamName];
    return stadiumLocations[officialName];
  }
  
  // Exact match
  if (stadiumLocations[teamName]) {
    return stadiumLocations[teamName];
  }
  
  // Partial match
  for (const [team, location] of Object.entries(stadiumLocations)) {
    if (teamName.includes(team) || team.includes(teamName)) {
      return location;
    }
  }
  
  return 'Location Unknown';
}

// Fetch NFL games
async function fetchNFLGames() {
  try {
    document.getElementById('nfl-games').innerHTML = '<div class="loading">Loading NFL games...</div>';
    
    const response = await fetch(
      `https://corsproxy.io/?https://api.the-odds-api.com/v4/sports/americanfootball_nfl/odds/?apiKey=${ODDS_API_KEY}&regions=us&markets=h2h`
    );
    
    const data = await response.json();
    displayGames(data, 'nfl-games', 'NFL');
  } catch (error) {
    console.error('Error fetching NFL games:', error);
    document.getElementById('nfl-games').innerHTML = '<div class="loading">Error loading NFL games. Please try again.</div>';
  }
}

// Fetch CFB games
async function fetchCFBGames() {
  try {
    document.getElementById('cfb-games').innerHTML = '<div class="loading">Loading CFB games...</div>';
    
    const response = await fetch(
      `https://corsproxy.io/?https://api.the-odds-api.com/v4/sports/americanfootball_ncaaf/odds/?apiKey=${ODDS_API_KEY}&regions=us&markets=h2h`
    );
    
    const data = await response.json();
    displayGames(data, 'cfb-games', 'CFB');
  } catch (error) {
    console.error('Error fetching CFB games:', error);
    document.getElementById('cfb-games').innerHTML = '<div class="loading">Error loading CFB games. Please try again.</div>';
  }
}

// Display games
function displayGames(games, containerId, league) {
  const container = document.getElementById(containerId);
  
  if (!games || games.length === 0) {
    container.innerHTML = '<div class="loading">No upcoming games available</div>';
    return;
  }

  // Filter out games with unknown teams
  const knownGames = games.filter(game => {
    const homeTeamKnown = isKnownTeam(game.home_team);
    const awayTeamKnown = isKnownTeam(game.away_team);
    return homeTeamKnown && awayTeamKnown;
  });

  if (knownGames.length === 0) {
    container.innerHTML = '<div class="loading">No games available for major teams</div>';
    return;
  }

  // Sort by commence_time
  knownGames.sort((a, b) => new Date(a.commence_time) - new Date(b.commence_time));

  // Take first 12 games
  const gamesToShow = knownGames.slice(0, 12);

  container.innerHTML = gamesToShow.map(game => {
    const gameTime = new Date(game.commence_time);
    const now = new Date();
    const isToday = gameTime.toDateString() === now.toDateString();
    
    const timeString = gameTime.toLocaleTimeString('en-US', { 
      hour: 'numeric', 
      minute: '2-digit',
      hour12: true 
    });
    
    const dateString = isToday ? 'Today' : gameTime.toLocaleDateString('en-US', { 
      month: 'short', 
      day: 'numeric' 
    });

    const awayTeam = game.away_team;
    const homeTeam = game.home_team;
    const stadiumLocation = getStadiumLocation(homeTeam);
    
    return `
      <div class="game-card" data-home="${homeTeam}" data-away="${awayTeam}" data-location="${stadiumLocation}">
        <div class="stadium-info">${stadiumLocation}</div>
        <div class="teams">
          <div class="team">
            <div class="team-logo">${getTeamInitials(awayTeam)}</div>
            <span class="team-name" title="${awayTeam}">${truncateTeamName(awayTeam)}</span>
          </div>
          <div class="team">
            <div class="team-logo">${getTeamInitials(homeTeam)}</div>
            <span class="team-name" title="${homeTeam}">${truncateTeamName(homeTeam)}</span>
          </div>
        </div>
        <div class="game-time">${dateString} • ${timeString}</div>
        <div class="game-location">📍 ${stadiumLocation}</div>
      </div>
    `;
  }).join('');
}

// Get team initials for logo
function getTeamInitials(teamName) {
  const words = teamName.split(' ').filter(word => 
    !['the', 'of', 'and'].includes(word.toLowerCase())
  );
  
  if (words.length >= 2) {
    return (words[0][0] + words[words.length - 1][0]).toUpperCase();
  }
  return teamName.substring(0, 2).toUpperCase();
}

// Truncate long team names
function truncateTeamName(name) {
  name = name.replace(/(Crimson Tide|Buckeyes|Bulldogs|Wolverines|Longhorns|Sooners|Trojans|Fighting Irish|Tigers|Gators|Seminoles|Aggies|Ducks|Nittany Lions|Red Raiders|Roadrunners|Miners|Rainbow Warriors|Wildcats|Bears|Cardinals|Eagles|Cowboys|Falcons)/gi, '');
  name = name.trim();
  
  return name.length > 18 ? name.substring(0, 16) + '...' : name;
}

// Load games on page load
document.addEventListener('DOMContentLoaded', () => {
  fetchNFLGames();
  fetchCFBGames();
  
  // Refresh games every 5 minutes
  setInterval(() => {
    fetchNFLGames();
    fetchCFBGames();
  }, 5 * 60 * 1000);
});
