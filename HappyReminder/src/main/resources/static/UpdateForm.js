$(document).ready(function() {
   // Capture form submission event
	$("form").submit(function(event) {
       	// Prevent the default form submission behavior
		event.preventDefault();
		console.log("hello!")
	    const urlParams = new URLSearchParams(window.location.search);
	
	    // Add the query parameter
	    urlParams.set('personName', document.getElementById("personName").value); // Replace 'parameterName' with your parameter name
		urlParams.set('birthDate', document.getElementById("birthDate").value);
		urlParams.set('relation', document.getElementById("relation").value);
		urlParams.set('personName', document.getElementById("personName").value);
		urlParams.set('reminderDate', document.getElementById("reminderDate").value);
		urlParams.set('timezone', document.getElementById("timezone").value);
		
	    // Get the updated URL with the query parameter
	    const updatedUrl = `${window.location.pathname}?${urlParams.toString()}`;
	    
	    window.location.href = updatedUrl;

       // Optionally, you can return false to prevent further event propagation
       // and prevent the form from submitting traditionally.
       return false;
   });
});

let url = window.location.href;
if(url.includes('?')){
	console.log('Parameterised URL');
	//var spn = document.getElementById("SpringPersonName").outerText;
	addPeopleToUI();
} else {
	console.log('No Parameters in URL');
	addPeopleToUI();
}

function addPeopleToUI() {
	var obj = document.getElementById("SpringObj");
	console.log(obj.innerText);
	var arr = obj.innerText.replace(/\[|\]/g,'').split(',');
	console.log(arr);
	var div = document.createElement('div');
	var uuid;
	var count = 0;
	for (var x = 0; x < arr.length; x++) {
		
		if(arr[x].includes("{")) {
			uuid = arr[x].split("=")[1];
			console.log(uuid);
			div = document.createElement('div');
			div.id=uuid;
			div.className="card";
			continue;
		}
		
		var split = arr[x].split('=');
		
		if (split[1].includes("}")) {
			//var remove = split[1].split("}")[0];
			//div.innerHTML += '<h3 class="text-display">' + remove + '</h3>';
			var item = div.id;
			div.innerHTML += `<button class="close" style="color:red;" onClick='deletePerson("${item}")'>x</button>`;
			document.getElementById('boxContainer').appendChild(div);
			//console.log("Added");
		} else {
			//console.log(count)
			if (count == 0) {
				div.innerHTML += '<p class="text-display">' + "<b>Name:</b> " + split[1] + '</p>';
				count++;
			} else if (count == 1) {
				div.innerHTML += '<p class="text-display">' + "<b>Relation:</b> " + split[1] + '</p>';
				count++;
			} else if (count == 2) {
				var dob = moment(split[1]).format('DD MMMM YYYY');
				div.innerHTML += '<p class="text-display">' + "<b>Birthdate:</b> " + dob + '</p>';
				count++;
			} else if (count == 3) {
				var dob = moment(split[1]).format('DD MMMM YYYY');
				div.innerHTML += '<p class="text-display">' + "<b>Reminder date:</b> " + dob + '</p>';
				count++;
			}
			if (count > 3){
				count = 0;
			}
		}
	}
}

function deletePerson(id) {
	if(window.confirm("Are you sure want to delete this?")) {
		document.getElementById("boxContainer").removeChild(document.getElementById(id));
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/user");
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.send(id);
		xhr.onload = function() {
		  if (xhr.status === 200) {
		    console.log('Success!');
		  } else {
		    console.log('Error!');
		  }
		}
	}

}




