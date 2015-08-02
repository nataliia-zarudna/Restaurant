<html>
<body>
	<h1>${message}</h1>

    <form action="/addDish" method="post" >

        <input name="sectionID" value="1" />
        <input name="title" value="1" />
        <input name="icon" value="1" />
        <input name="price" value="1" />
        <input name="description" value="1" />
        <input name="isAvailable" value="true" />

        <input type="submit" name="Create Dish" />
    </form>

    <form action="/addSection" method="post" >

        <input name="title" value="1" />
        <input name="icon" value="1" />
        <input name="description" value="1" />

        <input type="submit" name="Create Dish" />
    </form>

</body>
</html>