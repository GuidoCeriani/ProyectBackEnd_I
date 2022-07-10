window.addEventListener('load', function () {
    (function(){

      //con fetch invocamos a la API de odontologos con el método GET
      //nos devolverá un JSON con una colección de odontologos
      const url = '/odontologos';
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
        var table = document.getElementById("odontologoTable");
        var odontologoRow =table.insertRow();
        let tr_id = 'tr_' + info[i].id;
        odontologoRow.id = tr_id;


        //armamos cada columna de la fila
        odontologoRow.innerHTML = '<td class=\"td_nombre\">' + info[i].nombre.toUpperCase() + '</td>' +
                '<td class=\"td_apellido\">' + info[i].apellido.toUpperCase() + '</td>';

    };

})
})

(function(){
  let pathname = window.location.pathname;
  if (pathname == "/odontologosLista.html") {
      document.querySelector(".nav .nav-item a:last").addClass("active");
  }
})
})