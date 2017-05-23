var tirApiURL = "https://api.turku.fi/feedback/v1/requests.json"
var smApiUrl = "https://apinf.io:3002/turku_street_maintenance_v1/vehicles/?limit=10&since=2017"
var key = ""

var  d = new Date();
var aika = d.getFullYear() + '-' +(d.getMonth() + 1) + '-' + d.getDate();

var weekago = new Date(d.getTime() - (24*60*60*1000)*7);
var alku = weekago.getFullYear() + '-' +(weekago.getMonth() + 1) + '-' + weekago.getDate();

//?start_date=2017-05-15T00:00:00Z&end_date=2017-05-21T00:00:00Z
$(document).ready(function() {
	$.getJSON(tirApiURL + "?start_date=" + alku + "T00:00:00Z&end_date=" + aika + "T23:55:00Z" , function(items) {
	// $.getJSON(smApiUrl, function(items2) {

	let lista = JSON.stringify(items);
    let luettelo;

	//todo: fix/create second api (items2) 
	
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
        var koord = {lat: tieto.lat, lng: tieto.long};
        addMarker(koord, map, tieto.service_name);
      }
	  else {
		addMarker(turku, map);
		  
	  }
    });
	// $.getJSON(smApiUrl, function(koira) {
	// let kissa = JSON.stringify(koira);
    $('#content').append(lista);
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
