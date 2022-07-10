window.addEventListener('load', function () {
    (function(){

      //con fetch invocamos a la API de odontologos con el método GET
      //nos devolverá un JSON con una colección de odontologos
      const url = '/turnos';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(function(respuesta){
            return respuesta.json();
            })
      .then(function (info) {
        console.log(info);
      //recorremos la colección de odontologos del JSON
for(let i=0;i<info.length;i++){
        //por cada odontologo armaremos una fila de la tabla
        //cada fila tendrá un id
        var table = document.getElementById("turnoTable");
        var turnoRow =table.insertRow();
        let tr_id = 'tr_' + info[i].id;
        turnoRow.id = tr_id;


        //armamos cada columna de la fila
        turnoRow.innerHTML = '<td class=\"td_nombre\">' + info[i].paciente.nombre.toUpperCase() + '</td>' +
                '<td class=\"td_apellido\">' + info[i].paciente.apellido.toUpperCase() + '</td>' +
                '<td class=\"td_fecha\">' + info[i].fecha.toString() + '</td>' +
                '<td class=\"td_Odontologo\">' + info[i].odontologo.apellido.toUpperCase() + '</td>';

    };

})
})

(function(){
  let pathname = window.location.pathname;
  if (pathname == "/turnosLista.html") {
      document.querySelector(".nav .nav-item a:last").addClass("active");
  }
})
})