/**
 * Created by Nataliia on 01.08.2015.
 */
$(document).ready(init);

function init() {

    $("#createDishBtn").on("click", function () {

        $("#uploadIcon").submit();

        var inputs = $("#addDishForm").find("input");

        var data = "";
        for (var i = 0; i < inputs.length; i++) {

            var input = $(inputs[i]);
            var name = input.attr("name");
            if (name != "file" && name != "name") {
                var value = input.attr("value");
                data += name + "=" + value + "&";
            }
        }

        data = data.substring(0, data.length - 1);
        console.log(data + data);

        $.ajax({
            url: 'addDish',
            type: 'POST',
            data: data,
            success: function(data) {
                console.log("Dish has been successfully created");
            }
        });



    });

    $(".sectionTitle").on("blur", function (e, data) {

        var sectionID = $(this).attr("sectionID");
        var newTitle = $(this).text().trim();

        $.ajax({
            url: 'updateSection',
            type: 'POST',
            data: "id=" + sectionID + "&title=" + newTitle,
            success: function (data) {
                console.log("Section title has been updated");
            }
        });
    });

    $("[dishEditableParam]").on("blur", function (e, data) {

        var parentLI = $(this).closest('li');

        var sectionID = parentLI.attr('sectionID');
        var dishID = parentLI.attr('dishID');
        var params = parentLI.find("[dishEditableParam]");

        var data = "";
        for (var i = 0; i < params.length; i++) {
            var name = $(params[i]).attr('dishEditableParam');
            var value = $(params[i]).html().trim();

            data += name + "=" + value + "&";
        }

        console.log("data " + data);

        data += "id=" + dishID + "&sectionID=" + sectionID + "&icon=thumb-1.jpg";

        $.ajax({
            url: 'updateDish',
            type: 'POST',
            data: data,
            success: function (data) {
                console.log("Dish has been updated");
            }
        });

    });
}