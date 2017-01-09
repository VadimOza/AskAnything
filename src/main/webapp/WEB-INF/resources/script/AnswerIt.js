/**
 * Created by VadimOz on 12.12.16.
 */
var count = 0;
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var formToHide;

function answerItJquery() {
    count = parseInt($("#countOfQuestions").text());
    zeroQuestions();
    $(".answerAll").on('click', function () {
        var question = $(this).parent().find("#question").text();
        var answer = $(this).parent().find("#answer").val();
        var date = $(this).parent().find("#date").text();
        formToHide = $(this).parent();

        $.ajax({
            dataType: 'json',
            contentType: "application/json",
            url: '/user/asynkAnswers',
            type: 'POST',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: JSON.stringify({'question': question, 'answer': answer, 'date': date}),
            success: function () {
                count--;
                zeroQuestions();
                formToHide.slideUp()(1000);
            },
            error: function (text) {
                alert("Errror!!" + text.responseText);
            }
        }).send();

    });

    $('.regform').submit(function () {
        var password = document.getElementById("password").value;
        var confirmPassword = document.getElementById("confirm").value;
        if (password === confirmPassword)
            return true;
        $('#password').css("border", "2px solid red");
        $('#confirm').css("border", "2px solid red");
        return false;
    });

    $.each($('.time,.time_b'), function(index){
        var tere = moment($(this).text());
        $(this).text(tere.fromNow());
    });
}

function zeroQuestions() {
    if (count === 0) {
        $('#noQuestions').slideDown(1000);
    } else {
        $('#noQuestions').hide();
    }
}
$(document).ready(answerItJquery);



