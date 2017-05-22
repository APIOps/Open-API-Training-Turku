var sourceURL = "https://api.turku.fi/feedback/v1/requests.json"
var key = ""

$(document).ready(function() {
  $.getJSON(sourceURL + "?api_key=" + key, function(items) {
    $('#content').append("<h2>Found again " + JSON.stringify(items) + " items</h2>");
  
  });
});
