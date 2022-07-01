
$(document).ready(function(e){
    var title = $('input[name="title"]');
    var author = $('input[name="author"]');
    var content = $('textarea[name="content"]');

    $('.saveButton').click(function(e){
        var request = {
            title:title.val(),
            author:author.val(),
            content:content.val(),
        }

        console.log(request);

        //등록 ajax 시작
        $.ajax({
            url: '/api/v1/posts',
            type: 'POST',
            data: JSON.stringify(request),
            contentType:"application/json; charset=utf-8",
            dataType: "text",
            success: function(result){
            console.log(result);
            location.href="http://localhost:8080/"
            },
             error: function(error){
            alert(JSON.stringify(error));
}
        })
        //등록 ajax 끝
    })
})