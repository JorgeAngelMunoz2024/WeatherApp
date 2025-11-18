var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g = Object.create((typeof Iterator === "function" ? Iterator : Object).prototype);
    return g.next = verb(0), g["throw"] = verb(1), g["return"] = verb(2), typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (g && (g = 0, op[0] && (_ = 0)), _) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
// @ts-ignore - config is loaded from external config.js
var TOKEN = config.TOKEN;
// Stadium locations for NFL and CFB teams
var stadiumLocations = {
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
function getStadiumLocation(teamName) {
    // Try exact match first
    if (stadiumLocations[teamName]) {
        return stadiumLocations[teamName];
    }
    // Try partial match
    for (var _i = 0, _a = Object.entries(stadiumLocations); _i < _a.length; _i++) {
        var _b = _a[_i], team = _b[0], location_1 = _b[1];
        if (teamName.includes(team) || team.includes(teamName)) {
            return location_1;
        }
    }
    return 'Location Unknown';
}
// Fetch NFL games
function fetchNFLGames() {
    return __awaiter(this, void 0, void 0, function () {
        var nflGamesElement, response, data, error_1, nflGamesElement;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    _a.trys.push([0, 3, , 4]);
                    nflGamesElement = document.getElementById('nfl-games');
                    if (nflGamesElement) {
                        nflGamesElement.innerHTML = '<div class="loading">Loading NFL games...</div>';
                    }
                    return [4 /*yield*/, fetch("https://corsproxy.io/?https://api.the-odds-api.com/v4/sports/americanfootball_nfl/odds/?apiKey=".concat(TOKEN, "&regions=us&markets=h2h"))];
                case 1:
                    response = _a.sent();
                    return [4 /*yield*/, response.json()];
                case 2:
                    data = _a.sent();
                    displayGames(data, 'nfl-games', 'NFL');
                    return [3 /*break*/, 4];
                case 3:
                    error_1 = _a.sent();
                    console.error('Error fetching NFL games:', error_1);
                    nflGamesElement = document.getElementById('nfl-games');
                    if (nflGamesElement) {
                        nflGamesElement.innerHTML = '<div class="loading">Error loading NFL games. Please try again.</div>';
                    }
                    return [3 /*break*/, 4];
                case 4: return [2 /*return*/];
            }
        });
    });
}
// Fetch CFB games
function fetchCFBGames() {
    return __awaiter(this, void 0, void 0, function () {
        var cfbGamesElement, response, data, error_2, cfbGamesElement;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    _a.trys.push([0, 3, , 4]);
                    cfbGamesElement = document.getElementById('cfb-games');
                    if (cfbGamesElement) {
                        cfbGamesElement.innerHTML = '<div class="loading">Loading CFB games...</div>';
                    }
                    return [4 /*yield*/, fetch("https://corsproxy.io/?https://api.the-odds-api.com/v4/sports/americanfootball_ncaaf/odds/?apiKey=".concat(TOKEN, "&regions=us&markets=h2h"))];
                case 1:
                    response = _a.sent();
                    return [4 /*yield*/, response.json()];
                case 2:
                    data = _a.sent();
                    displayGames(data, 'cfb-games', 'CFB');
                    return [3 /*break*/, 4];
                case 3:
                    error_2 = _a.sent();
                    console.error('Error fetching CFB games:', error_2);
                    cfbGamesElement = document.getElementById('cfb-games');
                    if (cfbGamesElement) {
                        cfbGamesElement.innerHTML = '<div class="loading">Error loading CFB games. Please try again.</div>';
                    }
                    return [3 /*break*/, 4];
                case 4: return [2 /*return*/];
            }
        });
    });
}
// Display games
function displayGames(games, containerId, league) {
    var container = document.getElementById(containerId);
    if (!container)
        return;
    if (!games || games.length === 0) {
        container.innerHTML = '<div class="loading">No upcoming games available</div>';
        return;
    }
    // Sort by commence_time
    games.sort(function (a, b) { return new Date(a.commence_time).getTime() - new Date(b.commence_time).getTime(); });
    // Take first 12 games
    var gamesToShow = games.slice(0, 12);
    container.innerHTML = gamesToShow.map(function (game) {
        var gameTime = new Date(game.commence_time);
        var now = new Date();
        // Check if game is today
        var isToday = gameTime.toDateString() === now.toDateString();
        var timeString = gameTime.toLocaleTimeString('en-US', {
            hour: 'numeric',
            minute: '2-digit',
            hour12: true
        });
        var dateString = isToday ? 'Today' : gameTime.toLocaleDateString('en-US', {
            month: 'short',
            day: 'numeric'
        });
        var awayTeam = game.away_team;
        var homeTeam = game.home_team;
        var stadiumLocation = getStadiumLocation(homeTeam);
        return "\n      <div class=\"game-card\" data-home=\"".concat(homeTeam, "\" data-away=\"").concat(awayTeam, "\" data-location=\"").concat(stadiumLocation, "\">\n        <div class=\"stadium-info\">").concat(stadiumLocation, "</div>\n        <div class=\"teams\">\n          <div class=\"team\">\n            <div class=\"team-logo\">").concat(getTeamInitials(awayTeam), "</div>\n            <span class=\"team-name\" title=\"").concat(awayTeam, "\">").concat(truncateTeamName(awayTeam), "</span>\n          </div>\n          <div class=\"team\">\n            <div class=\"team-logo\">").concat(getTeamInitials(homeTeam), "</div>\n            <span class=\"team-name\" title=\"").concat(homeTeam, "\">").concat(truncateTeamName(homeTeam), "</span>\n          </div>\n        </div>\n        <div class=\"game-time\">").concat(dateString, " \u2022 ").concat(timeString, "</div>\n        <div class=\"game-location\">\uD83D\uDCCD ").concat(stadiumLocation, "</div>\n      </div>\n    ");
    }).join('');
}
// Get team initials for logo
function getTeamInitials(teamName) {
    var words = teamName.split(' ').filter(function (word) {
        return !['the', 'of', 'and'].includes(word.toLowerCase());
    });
    if (words.length >= 2) {
        return (words[0][0] + words[words.length - 1][0]).toUpperCase();
    }
    return teamName.substring(0, 2).toUpperCase();
}
// Truncate long team names
function truncateTeamName(name) {
    // Remove common suffixes for display
    name = name.replace(/(Crimson Tide|Buckeyes|Bulldogs|Wolverines|Longhorns|Sooners|Trojans|Fighting Irish|Tigers|Gators|Seminoles|Aggies|Ducks|Nittany Lions)/gi, '');
    name = name.trim();
    return name.length > 18 ? name.substring(0, 16) + '...' : name;
}
// Load games on page load
document.addEventListener('DOMContentLoaded', function () {
    fetchNFLGames();
    fetchCFBGames();
    // Refresh games every 5 minutes
    setInterval(function () {
        fetchNFLGames();
        fetchCFBGames();
    }, 5 * 60 * 1000);
});
