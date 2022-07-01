

$(document).ready(function(e){
    var title = $('input[name="title"]');
    var content = $('textarea[name="content"]');
    var id = $('input[name="id"]').val();
    console.log(id);

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

    $('.editButton').click(function(e){
        var request = {
        title:title.val(),
        content:content.val(),
        }
        console.log(request);

          $.ajax({
                    url: '/api/v1/posts/' + id,
                    type: 'PUT',
                    data: JSON.stringify(request),
                    contentType:"application/json; charset=utf-8",
                    dataType: "text",
                    success: function(result){
                    console.log(result);
                    location.href="http://localhost:8080/posts/"+id
                    }
                })


    })

})