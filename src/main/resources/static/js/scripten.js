var basePath = 'http://localhost:8080/api';

$(document).ready(function() {
    ControllManager.homeState();
    $('#NewUserForm').hide();
    $('#ChangeUserForm').hide();
    $('#newCategoryForm').hide();
    $('#setMetadata').hide();
    $('#changeBookTab').hide();
    $('#AdvancedSearch').hide();
    $('#LanguageSearch').hide();
    $('#btnUseSmpSearch').hide();
    UserManager.loadUser();
    LanguageManager.populateQueryLangBox();
    LanguageManager.populateLangSearchBox();

    $('#switchToSR').click(function(e) {
        e.preventDefault();
        window.location = "http://localhost:8080/rs/index.html";
    });

    $('#HomeBtn').click(function(e) {
        e.preventDefault();
        ControllManager.homeState();
        LanguageManager.populateQueryLangBox();
        LanguageManager.populateLangSearchBox();
        $('#simpleSearchBox').val("");
        $('#BooksResult').empty();
    });
    $('#AddBookBtn').click(function(e) {
        e.preventDefault();
        CategoryManager.populateCatBox();
        LanguageManager.populateLangBox();
        ControllManager.addBookState();
        $('#uploadBookForm').show();
        $('#setMetadata').hide();
        $('#bookFileInput').val("");
        $('#indexBookResult').empty();

    });
    $('#LoginBtn').click(function(e) {
        e.preventDefault();
        ControllManager.loginState();
    });
    $('#CategoriesBtn').click(function(e) {
        e.preventDefault();
        ControllManager.categoriesState();
        CategoryManager.loadCategories();
        $('#changeBookTab').hide();
    });
    $('#UsersBtn').click(function(e) {
        e.preventDefault();
        ControllManager.usersState();
        UserManager.loadUsers();
    });
    $('#SubscribesBtn').click(function(e) {
        e.preventDefault();
        ControllManager.subscribeState();
        CategoryManager.populateSubCatBox();
        $('#SubUserUsername').val("");
    });
    $('#ProfileBtn').click(function(e) {
        e.preventDefault();
        ControllManager.profileState();
    });
    $('#LogoutBtn').click(function(e) {
        e.preventDefault();
        UserManager.logout();
    });
    $('#btnConfirmLogin').click(function(e) {
        e.preventDefault();
        UserManager.login();
    });

    $('#btnConfirmChange').click(function(e) {
        e.preventDefault();
        if($('#ProfilePassword').val() == $('#ProfilePasswordRepeat').val()){
            UserManager.changeUser(true);
        }
        else {
            alert("Password and repeated password must be same!")
        }
    });
    $('#btnUseLangSearch').click(function(e) {
        e.preventDefault();
        $('#BooksResult').empty();
        $('#AdvancedSearch').hide();
        $('#SimpleSearch').hide();
        $('#LanguageSearch').show();
        $('#btnUseAdvSearch').show();
        $('#btnUseSmpSearch').show();
        $('#btnUseLangSearch').hide();
    });
    $('#btnUseAdvSearch').click(function(e) {
        e.preventDefault();
        $('#BooksResult').empty();
        $('#AdvancedSearch').show();
        $('#SimpleSearch').hide();
        $('#LanguageSearch').hide();
        $('#btnUseAdvSearch').hide();
        $('#btnUseSmpSearch').show();
        $('#btnUseLangSearch').show();
    });
    $('#btnUseSmpSearch').click(function(e) {
        e.preventDefault();
        $('#BooksResult').empty();
        $('#SimpleSearch').show();
        $('#AdvancedSearch').hide();
        $('#LanguageSearch').hide();
        $('#btnUseAdvSearch').show();
        $('#btnUseSmpSearch').hide();
        $('#btnUseLangSearch').show();
    });
    $('#btnSimpleSearch').click(function(e) {
        e.preventDefault();
        SearchingManager.simpleSearch();
    });
    $('#btnAdvancedSearch').click(function(e) {
        e.preventDefault();
        SearchingManager.advancedSearch();
    });
    $('#btnLanguageSearch').click(function(e) {
        e.preventDefault();
        SearchingManager.languageSearch();
    });

    $('#btnNewUser').click(function(e) {
        e.preventDefault();
        $('#NewUserForm').toggle();
        $('#ChangeUserForm').hide();
    });
    $('#btnConfirmAddUser').click(function(e) {
        e.preventDefault();
        if ($('#UserFirstname').val() == "" || $('#UserLastname').val() == "" || $('#UserUsername').val() == ""
            || $('#UserPassword').val() == "") {
            alert("Some field is empty. You must fill all fields.")
        }
        else{
            UserManager.addUser();
        }
    });
    $('#btnConfirmChangeUser').click(function(e) {
        e.preventDefault();
        e.preventDefault();
        if($('#ChangeUserPassword').val() == $('#ChangeUserPasswordRep').val()){
            UserManager.changeUser(false);
        }
        else {
            alert("Password and repeated password must be same!")
        }
    });
    $('#btnNewCategoryConfirm').click(function(e) {
        e.preventDefault();
        CategoryManager.addCategory();
    });
    $('#btnSubmitSubscribe').click(function(e) {
        e.preventDefault();
        CategoryManager.subscribeUser($('#SubUserUsername').val(), $('#subCategoryBox').val());
    });
    $('#btnConfirmBookChange').click(function(e) {
        e.preventDefault();
        BookManager.confirmBookChange();
    });
    $('#btnSubmitBook').click(function(e) {
        e.preventDefault();
        IndexingManager.indexBook();
    });
    $('#btnConfirmMetaData').click(function(e) {
        e.preventDefault();
        IndexingManager.configMetadata();
    });
    $('#btnDeleteUser').click(function(e) {
        e.preventDefault();
        UserManager.deleteUser();
    });
});

var IndexingManager = {

    indexBook : function() {
        $('#btnSubmitBook').prop('disabled', true);
        $('#selLang').val($('#languageBox').val());
        $('#selCat').val($('#categoryBox').val());
        $('#userId').val(1);
        var form = $('#uploadBookForm')[0];
        var data = new FormData(form);
        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: basePath + '/index/add',
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            success: function (book) {
                $('#indexBookResult').empty();
                $('#indexBookResult').text("(Optional) Configure metadata...");
                $('#btnSubmitBook').prop('disabled', false);
                $('#uploadBookForm').hide();
                $('#setMetadata').show();
                $('#BookId').val(book.id);
                $('#BookFilename').val(book.fileName);
                $('#BookCreatedDate').val(book.createdDate);
                $('#BookTitle').val(book.title);
                $('#BookAuthor').val(book.author);
                $('#BookKeywords').val(book.keywords);
            },
            error: function (e) {
                $('#indexBookResult').empty();
                $('#indexBookResult').text(e.responseText);
                $('#btnSubmitBook').prop('disabled', false);
            }
        });
    },
    configMetadata : function() {
        $('#btnConfirmMetaData').prop('disabled', true);
        $.ajax({
            type: 'POST',
            url: basePath + '/index/metadata',
            contentType : 'application/json',
            data: IndexingManager.metaDataObj(),
            success : function(data) {
                $('#indexBookResult').empty();
                $('#indexBookResult').text("Metadata successfully changed.");
                $('#setMetadata').hide();
                $('#btnConfirmMetaData').prop('disabled', false);
            },
            error : function() {
                alert("error");
            }
        });
    },
    metaDataObj : function() {
        return JSON.stringify({
            "id" : $('#BookId').val(),
            "fileName" : $('#BookFilename').val(),
            "createdDate" : $('#BookCreatedDate').val(),
            "title" : $('#BookTitle').val(),
            "author" : $('#BookAuthor').val(),
            "keywords" : $('#BookKeywords').val()
        });
    }
};

var SearchingManager = {

    simpleSearch : function() {
        $.ajax({
            type: 'POST',
            url: basePath + '/search/simple',
            data: SearchingManager.simpleSearchData(),
            contentType : 'application/json',
            dataType : 'json',
            success : function(books) {
                $('#BooksResult').empty();
                $.each(books, function(index, book) {
                    $('#BooksResult').append(
                        '<hr><div>' +
                        '<p>Author: ' + book.author + '</p>' +
                        '<p>Title: ' + book.title + '</p>' +
                        '<p>Created date: ' + book.createdDate + '</p>' +
                        '<p>Keywords: ' + book.keywords + '</p>' +
                        '<p class="show-inline">Download: </p><button value="' + book.id
                        + '"class="btn btn-sm btn-primary bookIdDnw">' + book.fileName + '</button>' +
                        '</div>');
                });
                $('.bookIdDnw').click(function(e) {
                    e.preventDefault();
                    var id = $(this).val();

                    if($('#ProfileRole').val() == 'ADMINISTRATOR'){
                        BookManager.downloadBook(id);
                    }
                    else if($('#ProfileRole').val() == 'PRETPLATNIK'){
                        BookManager.checkSubscription(id);
                    }
                    else{
                        alert("Subscribe to selected category to be able to download the book.")
                    }
                });
            }
        });
    },
    simpleSearchData : function() {
        return JSON.stringify({
            "field" : $('#searcBySelect').val(),
            "value" : $('#simpleSearchBox').val(),
            "searchType" : $('input[name=searchType]:checked').val()
        });
    },
    advancedSearch : function() {
        $.ajax({
            type: 'POST',
            url: basePath + '/search/advanced',
            data: SearchingManager.advancedSearchData(),
            contentType : 'application/json',
            dataType : 'json',
            success : function(books) {
                $('#BooksResult').empty();
                $.each(books, function(index, book) {
                    $('#BooksResult').append(
                        '<hr><div>' +
                        '<p>Author: ' + book.author + '</p>' +
                        '<p>Title: ' + book.title + '</p>' +
                        '<p>Created date: ' + book.createdDate + '</p>' +
                        '<p>Keywords: ' + book.keywords + '</p>' +
                        '<p class="show-inline">Download: </p><button value="' + book.id
                        + '"class="btn btn-sm btn-primary bookIdDnw">' + book.fileName + '</button>' +
                        '</div>');
                });
                $('.bookIdDnw').click(function(e) {
                    e.preventDefault();
                    var id = $(this).val();

                    if($('#ProfileRole').val() == 'ADMINISTRATOR'){
                        BookManager.downloadBook(id);
                    }
                    else if($('#ProfileRole').val() == 'PRETPLATNIK'){
                        BookManager.checkSubscription(id);
                    }
                    else{
                        alert("Subscribe to selected category to be able to download the book.")
                    }
                });
            }
        })
    },
    advancedSearchData : function() {
        return JSON.stringify({
            "title" : {
                "value" : $('#titleSearchInput').val(),
                "operator" : $('#titleOperator').val()
            },
            "author" : {
                "value" : $('#authorSearchInput').val(),
                "operator" : $('#authorOperator').val()
            },
            "keywords" : {
                "value" : $('#keywordsSearchInput').val(),
                "operator" : $('#keywordsOperator').val()
            },
            "text" : {
                "value" : $('#textSearchInput').val(),
                "operator" : $('#textOperator').val()
            },
            "language" : {
                "value" : $('#queryLanguageBox').val(),
                "operator" : $('#languageOperator').val()
            },
            "queryType" : $('input[name=advancedSearchType]:checked').val()
        });
    },
    languageSearch : function() {
        $.ajax({
            type: 'POST',
            url: basePath + '/search/simple',
            data: SearchingManager.languageSearchData(),
            contentType : 'application/json',
            dataType : 'json',
            success : function(books) {
                $('#BooksResult').empty();
                $.each(books, function(index, book) {
                    $('#BooksResult').append(
                        '<hr><div>' +
                        '<p>Author: ' + book.author + '</p>' +
                        '<p>Title: ' + book.title + '</p>' +
                        '<p>Created date: ' + book.createdDate + '</p>' +
                        '<p>Keywords: ' + book.keywords + '</p>' +
                        '<p class="show-inline">Download: </p><button value="' + book.id
                        + '"class="btn btn-sm btn-primary bookIdDnw">' + book.fileName + '</button>' +
                        '</div>');
                });
                $('.bookIdDnw').click(function(e) {
                    e.preventDefault();
                    var id = $(this).val();

                    if($('#ProfileRole').val() == 'ADMINISTRATOR'){
                        BookManager.downloadBook(id);
                    }
                    else if($('#ProfileRole').val() == 'PRETPLATNIK'){
                        BookManager.checkSubscription(id);
                    }
                    else{
                        alert("Subscribe to selected category to be able to download the book.")
                    }
                });
            }
        });
    },
    languageSearchData : function() {
        return JSON.stringify({
            "field" : 'language',
            "value" : $('#languageSearchBox').val(),
            "searchType" : 'term'
        });
    }
};

var BookManager = {

    getBooksByCatId : function(catId) {
        $('#changeBookTab').hide();
        $.ajax({
            url : basePath + '/book/category/' + catId,
            dataType : 'json',
            success : function(books) {
                $('#BooksToShow').empty();
                $.each(books, function(index, book) {
                    $('#BooksToShow').append(
                        '<hr><div>' +
                        '<p>Author: ' + book.author + '</p>' +
                        '<p>Title: ' + book.title + '</p>' +
                        '<p>Created date: ' + book.createdDate + '</p>' +
                        '<p>Keywords: ' + book.keywords + '</p>' +
                        '<p class="show-inline">Download: </p><button value="' + book.id
                        + '"class="btn btn-sm btn-primary bookIdDnw">' + book.fileName + '</button>' +
                        '</div>');
                    if($('#ProfileRole').val() == 'ADMINISTRATOR'){
                        $('#BooksToShow').append(
                            '</p><button value="' + book.id + '" class="btn btn-danger bookIdDlt">Delete</button>' +
                            '</p><button value="' + book.id + '" class="btn btn-warning bookIdCng">Change</button>')
                    }
                });
                $('.bookIdDnw').click(function(e) {
                    e.preventDefault();
                    var id = $(this).val();

                    if($('#ProfileRole').val() == 'ADMINISTRATOR'){
                        BookManager.downloadBook(id);
                    }
                    else if($('#ProfileRole').val() == 'PRETPLATNIK'){
                        BookManager.checkSubscription(id);
                    }
                    else{
                        alert("Subscribe to selected category to be able to download the book.")
                    }
                });
                $('.bookIdDlt').click(function(e) {
                    e.preventDefault();
                    var bookId = $(this).val();
                    BookManager.deleteBook(bookId, catId);
                });
                $('.bookIdCng').click(function(e) {
                    e.preventDefault();
                    var id = $(this).val();
                    $('#changeBookTab').show();
                    BookManager.changeBookInfo(id);
                });
            }
        });
    },
    deleteBook : function(bookId, catId) {
        $.ajax({
            url : basePath + '/book/delete/' + bookId,
            type : "DELETE",
            success : function() {
                alert("Book successfully deleted!");
                BookManager.getBooksByCatId(catId);
            }
        });
    },
    checkSubscription : function(bookId) {
        $.ajax({
            url : basePath + '/category/subsribecheck/' + bookId,
            success : function() {
                BookManager.downloadBook(bookId);
            },
            error : function() {
                alert("You are not able to download the books in this category.")
            }
        });
    },
    downloadBook : function(bookId) {
        window.location = basePath + '/book/download/' + bookId
    },
    changeBookInfo : function(bookId) {
        $.ajax({
            url : basePath + "/book/" + bookId,
            success : function(book) {
                $('#BookIdNew').val(book.id);
                $('#BookTitleNew').val(book.title);
                $('#BookAuthorNew').val(book.author);
                $('#BookKeywordsNew').val(book.keywords);
            }
        });
    },
    confirmBookChange : function() {
        $.ajax({
            url : basePath + '/book/change',
            type : 'POST',
            contentType : 'application/json',
            data : BookManager.confirmBookChangeData(),
            success : function(bookId) {
                BookManager.getBooksByCatId(bookId);
            }
        });
    },
    confirmBookChangeData : function() {
        return JSON.stringify({
            "id" : $('#BookIdNew').val(),
            "title" : $('#BookTitleNew').val(),
            "author" : $('#BookAuthorNew').val(),
            "keywords" : $('#BookKeywordsNew').val()
        });
    }
};

var UserManager = {

    login : function() {
        $.ajax({
            url : basePath + '/user/login',
            type : 'POST',
            contentType : 'application/json',
            data : UserManager.loginData(),
            success : function(data) {
                UserManager.loadUser();
                ControllManager.homeState();
                $('#LoginUsername').val("");
                $('#LoginPassword').val("");
            },
            error : function(e) {
                alert(e.responseText);
            }

        });
    },
    loginData : function() {
        return JSON.stringify({
            "username" : $('#LoginUsername').val(),
            "password" : $('#LoginPassword').val()
        });
    },
    logout : function() {
        $.ajax({
            url : basePath + '/user/logout',
            success : function() {
                UserManager.loadUser();
                ControllManager.homeState();
                $('#ProfileId').val("");
                $('#ProfileUsername').val("");
                $('#ProfileFirstname').val("");
                $('#ProfileLastname').val("");
                $('#ProfileRole').val("");
                $('#ProfilePassword').val("");
                $('#ProfilePasswordRepeat').val("");
            }
        });
    },
    loadUser : function() {
        $.ajax({
            url : basePath + '/user/loaduser',
            success : function(user) {
                ControllManager.loggedInState();
                $('#ProfileId').val(user.id);
                $('#ProfileUsername').val(user.username);
                $('#ProfileFirstname').val(user.firstname);
                $('#ProfileLastname').val(user.lastname);
                $('#ProfileRole').val(user.role);
                if(user.role == 'ADMINISTRATOR'){
                    ControllManager.adminModeON();
                }
                else{
                    ControllManager.adminModeOFF();
                }
            },
            error : function() {
                ControllManager.loggedOutState();
                ControllManager.adminModeOFF();
            }
        });
    },
    changeUser : function(isProfile) {
        $.ajax({
            url : basePath + '/user/change',
            type : 'POST',
            contentType : 'application/json',
            data : UserManager.changeUserData(isProfile),
            success : function(data) {
                if(isProfile == true){
                    UserManager.loadUser();
                    ControllManager.profileState();
                    alert("Password successfully changed!");
                }
                else{
                    UserManager.loadUsers();
                    ControllManager.usersState();
                    $('#ChangeUserForm').hide();
                }
            }
        });
    },
    changeUserData : function(isProfile) {
        if(isProfile == true){
            return JSON.stringify({
                "id" : $('#ProfileId').val(),
                "password" : $('#ProfilePassword').val(),
                "firstname" : $('#ProfileFirstname').val(),
                "lastname" : $('#ProfileLastname').val()
            });
        }
        else{
            return JSON.stringify({
                "id" : $('#ChangeUserId').val(),
                "password" : $('#ChangeUserPassword').val(),
                "firstname" : $('#ChangeUserFirstname').val(),
                "lastname" : $('#ChangeUserLastname').val()
            });
        }
    },
    loadUsers : function() {
        $.ajax({
            url : basePath + '/user',
            success : function(users) {
                $('#UsersList').empty();
                $.each(users, function(index, user) {
                    $('#UsersList').append(
                        '<div class="inline-element">' +
                        '<button value="' + user.id + '" class="buttonAsLink">' + user.username + '</button>' +
                        '<p>' + user.firstname + '</p>' +
                        '<p>' + user.lastname + '</p>' +
                        '<p>' + user.role + '</p></div>');
                });
                if($('#ProfileRole').val() == 'ADMINISTRATOR'){
                    $('.buttonAsLink').click(function(e) {
                        e.preventDefault();
                        var id = $(this).val();
                        UserManager.getUser(id);
                        $('#ChangeUserForm').show();
                        $('#NewUserForm').hide();
                    });
                }
            }
        });
    },
    getUser : function(id) {
        $.ajax({
            url : basePath + '/user/' + id,
            dataType : 'json',
            success : function(user) {
                $('#ChangeUserId').val(user.id)
                $('#ChangeUserUsername').val(user.username)
                $('#ChangeUserFirstname').val(user.firstname)
                $('#ChangeUserLastname').val(user.lastname)
            }
        });
    },
    addUser : function() {
        $.ajax({
            url : basePath + '/user/adduser',
            type : 'POST',
            contentType : 'application/json',
            data : UserManager.addUserData(),
            success : function() {
                UserManager.loadUsers();
                ControllManager.usersState();
                $('#NewUserForm').toggle();
                $('#UserUsername').val("");
                $('#UserPassword').val("");
                $('#UserFirstname').val("");
                $('#UserLastname').val("");
            },
            error : function() {
                alert("User with username: \"" + $('#UserUsername').val() + "\" already exist!");
            }
        });
    },
    addUserData : function() {
        return JSON.stringify({
            "username" : $('#UserUsername').val(),
            "password" : $('#UserPassword').val(),
            "firstname" : $('#UserFirstname').val(),
            "lastname" : $('#UserLastname').val()
        });
    },
    deleteUser : function() {
        $.ajax({
            type : "DELETE",
            url: basePath + '/user/delete/' + $('#ChangeUserId').val(),
            success : function() {
                UserManager.loadUsers();
                $('#ChangeUserForm').hide();
            }
        })
    }
};

var CategoryManager = {

    populateCatBox : function() {
        $.ajax({
            url: basePath + '/category',
            success: function(list) {
                $('#categoryBox').empty();
                $.each(list, function(index, category) {
                    $('#categoryBox').append('<option value="' + category.id + '">' + category.name + '</option>');
                });
            }
        });
    },
    populateSubCatBox : function() {
        $.ajax({
            url: basePath + '/category',
            success: function(list) {
                $('#subCategoryBox').empty();
                $('#subCategoryBox').append('<option value="0">All</option>');
                $.each(list, function(index, category) {
                    $('#subCategoryBox').append('<option value="' + category.id + '">' + category.name + '</option>');
                });
            }
        });
    },
    loadCategories : function() {
        $('#BooksToShow').empty();
        $.ajax({
            url : basePath + '/category',
            dataType : 'json',
            success : function(categories) {
                $('#deleteCategoryTab').empty();
                $('#CategoriesList').empty();
                if($('#ProfileRole').val() == 'ADMINISTRATOR'){
                    $('#CategoriesList').append(
                        '<button id="btnNewCategory" class="btn btn-success">New Category</button></p>');
                }
                $.each(categories, function(index, category) {
                    $('#CategoriesList').append(
                        '<button value="' + category.id + '" class="btn btn-primary catClass">' + category.name + '</button></p>');
                });
                $('.catClass').click(function(e) {
                    e.preventDefault();
                    var id = $(this).val();
                    BookManager.getBooksByCatId(id);

                    if($('#ProfileRole').val() == 'ADMINISTRATOR'){
                        $('#deleteCategoryTab').empty();
                        $('#deleteCategoryTab').append('<button class="btn btn-danger dltCategory">Delete category</button>');
                        $('.dltCategory').click(function(e) {
                            CategoryManager.deleteCategory(id);
                        });
                    }
                });
                $('#btnNewCategory').click(function(e) {
                    e.preventDefault();
                    $('#newCategoryForm').toggle();
                });
            }
        });
    },
    addCategory : function() {
        $.ajax({
            url : basePath + '/category/newcategory',
            type : 'POST',
            contentType : 'application/json',
            data : JSON.stringify({"name" : $('#NewCategoryName').val()}),
            success : function() {
                CategoryManager.loadCategories();
                ControllManager.categoriesState();
                $('#newCategoryForm').hide();
                $('#NewCategoryName').val("");
            }
        });
    },
    subscribeUser : function(username, catId) {
        $.ajax({
            url : basePath + '/category/subscribe/' + username + '/' + catId,
            success : function() {
                alert("User successfully subscribed!");
                $('#SubUserUsername').val("");
            }
        });
    },
    deleteCategory : function(catId) {
        $.ajax({
            type : 'DELETE',
            url : basePath + '/category/delete/' + catId,
            success : function() {
                CategoryManager.loadCategories();
            }
        });
    }
};

var LanguageManager = {

    populateLangBox : function() {
        $.ajax({
            url: basePath + '/language',
            success: function(list) {
                $('#languageBox').empty();
                $.each(list, function(index, lang) {
                    $('#languageBox').append('<option value="' + lang.id + '">' + lang.name + '</option>');
                });
            }
        });
    },
    populateQueryLangBox : function() {
        $.ajax({
            url: basePath + '/language',
            success: function(list) {
                $('#queryLanguageBox').empty();
                $('#queryLanguageBox').append('<option value="0">None</option>');
                $.each(list, function(index, lang) {
                    $('#queryLanguageBox').append('<option value="' + lang.id + '">' + lang.name + '</option>');
                });
            }
        });
    },
    populateLangSearchBox : function() {
        $.ajax({
            url: basePath + '/language',
            success: function(list) {
                $('#languageSearchBox').empty();
                $.each(list, function(index, lang) {
                    $('#languageSearchBox').append('<option value="' + lang.id + '">' + lang.name + '</option>');
                });
            }
        });
    }
};

var ControllManager = {

    loggedInState : function() {
        $('#LogoutBtn').show();
        $('#ProfileBtn').show();
        $('#LoginBtn').hide();
    },
    loggedOutState : function() {
        $('#LogoutBtn').hide();
        $('#ProfileBtn').hide();
        $('#LoginBtn').show();
    },
    adminModeON : function() {
        $('#UsersBtn').show();
        $('#AddBookBtn').show();
        $('#AdminInfo').show();
        $('#SubscribesBtn').show();
    },
    adminModeOFF : function() {
        $('#UsersBtn').hide();
        $('#AddBookBtn').hide();
        $('#AdminInfo').hide();
        $('#SubscribesBtn').hide();
    },
    homeState : function() {
        $('#SearchForm').show();
        $('#AddBookForm').hide();
        $('#LoginForm').hide();
        $('#CategoriesTab').hide();
        $('#UsersTab').hide();
        $('#SubscribeTab').hide();
        $('#ProfileForm').hide();
    },
    addBookState : function() {
        $('#SearchForm').hide();
        $('#AddBookForm').show();
        $('#LoginForm').hide();
        $('#CategoriesTab').hide();
        $('#UsersTab').hide();
        $('#SubscribeTab').hide();
        $('#ProfileForm').hide();
    },
    loginState : function() {
        $('#SearchForm').hide();
        $('#AddBookForm').hide();
        $('#LoginForm').show();
        $('#CategoriesTab').hide();
        $('#UsersTab').hide();
        $('#SubscribeTab').hide();
        $('#ProfileForm').hide();
    },
    categoriesState : function() {
        $('#SearchForm').hide();
        $('#AddBookForm').hide();
        $('#LoginForm').hide();
        $('#CategoriesTab').show();
        $('#UsersTab').hide();
        $('#SubscribeTab').hide();
        $('#ProfileForm').hide();
    },
    usersState : function() {
        $('#SearchForm').hide();
        $('#AddBookForm').hide();
        $('#LoginForm').hide();
        $('#CategoriesTab').hide();
        $('#UsersTab').show();
        $('#SubscribeTab').hide();
        $('#ProfileForm').hide();
    },
    subscribeState : function() {
        $('#SearchForm').hide();
        $('#AddBookForm').hide();
        $('#LoginForm').hide();
        $('#CategoriesTab').hide();
        $('#UsersTab').hide();
        $('#SubscribeTab').show();
        $('#ProfileForm').hide();
    },
    profileState : function() {
        $('#SearchForm').hide();
        $('#AddBookForm').hide();
        $('#LoginForm').hide();
        $('#CategoriesTab').hide();
        $('#UsersTab').hide();
        $('#SubscribeTab').hide();
        $('#ProfileForm').show();
    }
};