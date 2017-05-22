var sourceURL = "https://api.turku.fi/feedback/v1/requests.json?start_date=2017-05-01T00:00:00Z&end_date=2017-05-21T00:00:00Z"
var key = ""

$(document).ready(function() {
  $.getJSON(sourceURL + "?api_key=" + key, function(items) {
    $('#content').append("<h2>Found again " + JSON.stringify(items) + " items</h2>");
  
  });
});
