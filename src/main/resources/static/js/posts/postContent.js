
$(document).ready(function(e){
        var id = $('input[name="id"]').val();
    console.log('ready')
    $('.deleteButton').click(function(e){
        $.ajax({
            url: '/api/v1/posts/' + id,
            type: 'DELETE',
            contentType:"application/json; charset=utf-8",
            dataType: "text",
            success: function(result){
            console.log(result);
            location.href="http://localhost:8080/"
            }
        })

    })
})
