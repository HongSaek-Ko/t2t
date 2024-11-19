$(document).ready(function (){
    registerEvent(['#deleteBtn']);
});

function registerEvent(){
    $('#deleteBtn').on('click', function (e){
        console.log(e);

        $('#deletePrdModal').modal('show');
    });
}
