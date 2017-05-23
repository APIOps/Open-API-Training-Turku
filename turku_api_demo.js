var sourceURL = "https://api.turku.fi/feedback/v1/requests.json"
var key = ""
var  d = new Date();
var aika = d.getFullYear() + '-' +0+(d.getMonth() + 1) + '-' + d.getDate();
var alku = d.getFullYear() + '-' +0+(d.getMonth() + 1) + '-' + (d.getDate() - 7);
//var kk = d.getMonth();
//var dd = d.getDate();

//todo: make date 3 days ago to today

//?start_date=2017-05-15T00:00:00Z&end_date=2017-05-21T00:00:00Z
$(document).ready(function() {
  //$.getJSON(sourceURL + "?api_key=" + key, function(items) {
	$.getJSON(sourceURL + "?start_date=" + alku + "T00:00:00Z&end_date=" + aika + "T23:55:00Z" , function(items) {
    let lista = JSON.stringify(items);
    let luettelo;

    var turku = {lat: 60.4518126, lng: 22.266630299999974};
    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 12,
      center: turku
    });

    items.forEach(function(tieto) {
      $('#content').append(tieto.requested_datetime+' '+ tieto.service_name+' osoite: '+tieto.address+' lat='+tieto.lat+' lon='+tieto.long+'<br>');
      // drawMap(tieto.lat, tieto.long);
      // luettelo = luettelo + tieto +"<br>";
      if (tieto.lat && tieto.long) {
        var turku = {lat: tieto.lat, lng: tieto.long};
        addMarker(turku, map);
      }
    });
    $('#content').append(lista + " alku " + alku +" loppu "+ aika);
    // let lista;
    // let kpl = 0;
    // $('#content').append("Found <br>");
    // items.each($.parseJSON(items), function(key, value){
    //   lista = lista + key + "=" + value + "<br>";
    //   kpl++;
    // });
    // $('#content').append(kpl);


  });
});
