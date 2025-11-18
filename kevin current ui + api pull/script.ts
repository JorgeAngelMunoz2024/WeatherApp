// @ts-ignore - config is loaded from external config.js
const TOKEN: string = config.TOKEN;

// Interfaces
interface StadiumLocations {
  [teamName: string]: string;
}

interface OddsAPIGame {
  id: string;
  sport_key: string;
  sport_title: string;
  commence_time: string;
  home_team: string;
  away_team: string;
  bookmakers?: any[];
}

// Stadium locations for NFL and CFB teams
const stadiumLocations: StadiumLocations = {
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
  
  // CFB Teams (major programs)
  'Alabama': 'Tuscaloosa, AL',
  'Ohio State': 'Columbus, OH',
  'Georgia': 'Athens, GA',
  'Michigan': 'Ann Arbor, MI',
  'Texas': 'Austin, TX',
  'Oklahoma': 'Norman, OK',
  'USC': 'Los Angeles, CA',
  'Penn State': 'State College, PA',
  'LSU': 'Baton Rouge, LA',
  'Florida': 'Gainesville, FL',
  'Notre Dame': 'South Bend, IN',
  'Clemson': 'Clemson, SC',
  'Texas A&M': 'College Station, TX',
  'Auburn': 'Auburn, AL',
  'Oregon': 'Eugene, OR',
  'Florida State': 'Tallahassee, FL',
  'Tennessee': 'Knoxville, TN',
  'Ole Miss': 'Oxford, MS',
  'Mississippi State': 'Starkville, MS',
  'Arkansas': 'Fayetteville, AR',
  'Kentucky': 'Lexington, KY',
  'South Carolina': 'Columbia, SC',
  'Missouri': 'Columbia, MO',
  'Vanderbilt': 'Nashville, TN',
  'TCU': 'Fort Worth, TX',
  'Baylor': 'Waco, TX',
  'Oklahoma State': 'Stillwater, OK',
  'Kansas State': 'Manhattan, KS',
  'Kansas': 'Lawrence, KS',
  'Iowa State': 'Ames, IA',
  'West Virginia': 'Morgantown, WV',
  'Iowa': 'Iowa City, IA',
  'Wisconsin': 'Madison, WI',
  'Nebraska': 'Lincoln, NE',
  'Minnesota': 'Minneapolis, MN',
  'Northwestern': 'Evanston, IL',
  'Purdue': 'West Lafayette, IN',
  'Illinois': 'Champaign, IL',
  'Indiana': 'Bloomington, IN',
  'Rutgers': 'Piscataway, NJ',
  'Maryland': 'College Park, MD',
  'Michigan State': 'East Lansing, MI',
  'Washington': 'Seattle, WA',
  'UCLA': 'Pasadena, CA',
  'Stanford': 'Stanford, CA',
  'California': 'Berkeley, CA',
  'Arizona': 'Tucson, AZ',
  'Arizona State': 'Tempe, AZ',
  'Colorado': 'Boulder, CO',
  'Utah': 'Salt Lake City, UT',
  'Oregon State': 'Corvallis, OR',
  'Washington State': 'Pullman, WA',
  'NC State': 'Raleigh, NC',
  'North Carolina': 'Chapel Hill, NC',
  'Duke': 'Durham, NC',
  'Virginia': 'Charlottesville, VA',
  'Virginia Tech': 'Blacksburg, VA',
  'Miami': 'Miami Gardens, FL',
  'Georgia Tech': 'Atlanta, GA',
  'Louisville': 'Louisville, KY',
  'Pittsburgh': 'Pittsburgh, PA',
  'Syracuse': 'Syracuse, NY',
  'Boston College': 'Chestnut Hill, MA',
  'Wake Forest': 'Winston-Salem, NC',
  'SMU': 'Dallas, TX',
  'Tulane': 'New Orleans, LA',
  'Memphis': 'Memphis, TN',
  'Navy': 'Annapolis, MD',
  'Army': 'West Point, NY',
  'Air Force': 'Colorado Springs, CO',
  'BYU': 'Provo, UT',
  'UCF': 'Orlando, FL',
  'Cincinnati': 'Cincinnati, OH',
  'Houston': 'Houston, TX',
  'Boise State': 'Boise, ID',
  'San Diego State': 'San Diego, CA',
  'Fresno State': 'Fresno, CA',
};

// Get stadium location for a team
function getStadiumLocation(teamName: string): string {
  if (stadiumLocations[teamName]) {
    return stadiumLocations[teamName];
  }
  
  for (const [team, location] of Object.entries(stadiumLocations)) {
    if (teamName.includes(team) || team.includes(teamName)) {
      return location;
    }
  }
  
  return 'Location Unknown';
}

// Fetch NFL games
async function fetchNFLGames(): Promise<void> {
  try {
    const nflGamesElement = document.getElementById('nfl-games');
    if (nflGamesElement) {
      nflGamesElement.innerHTML = '<div class="loading">Loading NFL games...</div>';
    }
    
    const response = await fetch(
      `https://corsproxy.io/?https://api.the-odds-api.com/v4/sports/americanfootball_nfl/odds/?apiKey=${TOKEN}&regions=us&markets=h2h`
    );
    
    const data: OddsAPIGame[] = await response.json();
    displayGames(data, 'nfl-games', 'NFL');
  } catch (error) {
    console.error('Error fetching NFL games:', error);
    const nflGamesElement = document.getElementById('nfl-games');
    if (nflGamesElement) {
      nflGamesElement.innerHTML = '<div class="loading">Error loading NFL games. Please try again.</div>';
    }
  }
}

// Fetch CFB games
async function fetchCFBGames(): Promise<void> {
  try {
    const cfbGamesElement = document.getElementById('cfb-games');
    if (cfbGamesElement) {
      cfbGamesElement.innerHTML = '<div class="loading">Loading CFB games...</div>';
    }
    
    const response = await fetch(
      `https://corsproxy.io/?https://api.the-odds-api.com/v4/sports/americanfootball_ncaaf/odds/?apiKey=${TOKEN}&regions=us&markets=h2h`
    );
    
    const data: OddsAPIGame[] = await response.json();
    displayGames(data, 'cfb-games', 'CFB');
  } catch (error) {
    console.error('Error fetching CFB games:', error);
    const cfbGamesElement = document.getElementById('cfb-games');
    if (cfbGamesElement) {
      cfbGamesElement.innerHTML = '<div class="loading">Error loading CFB games. Please try again.</div>';
    }
  }
}

// Display games
function displayGames(games: OddsAPIGame[], containerId: string, league: string): void {
  const container = document.getElementById(containerId);
  
  if (!container) return;
  
  if (!games || games.length === 0) {
    container.innerHTML = '<div class="loading">No upcoming games available</div>';
    return;
  }

  games.sort((a, b) => new Date(a.commence_time).getTime() - new Date(b.commence_time).getTime());

  const gamesToShow = games.slice(0, 12);

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

    const awayTeam: string = game.away_team;
    const homeTeam: string = game.home_team;
    const stadiumLocation: string = getStadiumLocation(homeTeam);
    
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
function getTeamInitials(teamName: string): string {
  const words = teamName.split(' ').filter(word => 
    !['the', 'of', 'and'].includes(word.toLowerCase())
  );
  
  if (words.length >= 2) {
    return (words[0][0] + words[words.length - 1][0]).toUpperCase();
  }
  return teamName.substring(0, 2).toUpperCase();
}

// Truncate long team names
function truncateTeamName(name: string): string {
  name = name.replace(/(Crimson Tide|Buckeyes|Bulldogs|Wolverines|Longhorns|Sooners|Trojans|Fighting Irish|Tigers|Gators|Seminoles|Aggies|Ducks|Nittany Lions)/gi, '');
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
