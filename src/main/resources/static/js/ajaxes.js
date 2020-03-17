//изменение система или прибор
function onChangeType(select) {
    var selectedOption = select.options[select.selectedIndex];

    if (selectedOption.value === "device") {
        document.getElementById("deviceOrSystem").innerHTML = "прибора";
        document.querySelector('.device').style.display = '';
    } else {
        document.getElementById("deviceOrSystem").innerHTML = "системы";
        document.querySelector('.device').style.display = 'none';

    }
    //getListNames();
}

//изменение названия темы
function onChangeTopic(select) {
    //заполнение списка систем
    var topic = select.value;
    if (topic == "") {
        document.getElementById("systemTitle-id").innerHTML = "";
        return;
    } else {
        if (window.XMLHttpRequest) {
            // code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var objSel = document.getElementById("systemTitle-id");
                var myObj = JSON.parse(this.responseText);
                for (var i = 0; i < myObj.length; i++) {
                    objSel.options[i] = new Option(myObj[i].title, myObj[i].id);
                }
                getListNames();
            }
        };
        xmlhttp.open("GET", "/asrd/api/systems/titles?topicId=" + topic, true);
        xmlhttp.send();
    }

}

//заполнение списка приборов
function getListNames() {
    var n = document.getElementById("systemTitle-id").options.selectedIndex;
    var selected = document.getElementById("systemTitle-id").options[n].value;
    if (selected == "") {
        document.getElementById("deviceTitle-id").innerHTML = "";
        return;
    } else {
        if (window.XMLHttpRequest) {
            // code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var objSel = document.getElementById("deviceTitle-id");
                var myObj = JSON.parse(this.responseText);
                for (var i = 0; i < myObj.length; i++) {
                    objSel.options[i] = new Option(myObj[i].title, myObj[i].id);
                }
            }
        };
        xmlhttp.open("GET", "/asrd/api/devices/titles?systemTitleId=" + selected, true);
        xmlhttp.send();
    }
}

function onChangeSystem() {
    getListNames()
}