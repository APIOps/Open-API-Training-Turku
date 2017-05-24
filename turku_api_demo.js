var turkuIssueReportingApiURL = "https://api.turku.fi/feedback/v1/requests.json"
var streetMaintenanceApiUrl = "https://apinf.io:3002/turku_street_maintenance_v1/vehicles/?limit=10&since=2017"
var linkedEventsApiUrl = "http://linkedevents.api.lounaistieto.fi/v1/event/?start=today&end="
var key = ""

var  d = new Date();
var aika = d.getFullYear() + '-' +(d.getMonth() + 1) + '-' + d.getDate();

var tomorrow = new Date(d.getTime() + (24*60*60*1000));
var loppu = tomorrow.getFullYear() + '-' +(tomorrow.getMonth() + 1) + '-' + tomorrow.getDate();

var weekago = new Date(d.getTime() - (24*60*60*1000)*7);
var alku = weekago.getFullYear() + '-' +(weekago.getMonth() + 1) + '-' + weekago.getDate();

$(document).ready(function() {
  var turkuCoords = {lat: 60.4518126, lng: 22.266630299999974};
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 12,
    center: turkuCoords
  });
  $.getJSON(streetMaintenanceApiUrl, function(streetMaintenanceData) {
    
	streetMaintenanceData.forEach(function(streetMaintenanceItem) {
	  var lat2 = streetMaintenanceItem.last_location.coords[1];
	  var long2 = streetMaintenanceItem.last_location.coords[0];
	  var title2 = streetMaintenanceItem.id+' '+streetMaintenanceItem.last_location.events[0];
	  $('#VehicleData').append(' ID + Event ' +title2+' Latitude '+lat2 + ' Longitude ' + long2+'<br>');
	  // $('#content2').append(' Testing '+linkedEventsApiUrl + loppu + "&max_duration=86400"+'<br>');
	  var koord = {lat: lat2, lng: long2};
	  addMarkerY(koord, map, title2);
	});
  });  
  $.getJSON(turkuIssueReportingApiURL + "?start_date=" + alku + "T00:00:00Z&end_date=" + aika + "T23:55:00Z" , function(issueReportingData) {

	var turkuCoords = {lat: 60.4518126, lng: 22.266630299999974};

 	issueReportingData.forEach(function(issueItem) {
      $('#IssueData').append(issueItem.requested_datetime+' '+ issueItem.service_name+' osoite: '+issueItem.address+' lat='+issueItem.lat+' lon='+issueItem.long+'<br>');
	  if (issueItem.lat && issueItem.long) {
        var koord = {lat: issueItem.lat, lng: issueItem.long};
        addMarker(koord, map, issueItem.service_name);
      }
  	  else {
		addMarker(turkuCoords, map, issueItem.service_name);
	  }  
    }); 
  });
    $.getJSON(linkedEventsApiUrl + loppu + "&max_duration=86400" , function(linkedEventsData) {
	  var lista = JSON.stringify(linkedEventsData);
	  $('#EventData').append(lista);
	
	
	
	
	});
});