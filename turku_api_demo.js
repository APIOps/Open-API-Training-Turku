var sourceURL = "https://apinf.io:3002/turku_feedback_v1/services.json"
var key = ""

$(document).ready(function() {
  $.getJSON(sourceURL + "?api_key=" + key, function(items) {
    $('#content').append("<h2>Found again " + items.length + " items</h2>");
  });
  turku_map();
});
