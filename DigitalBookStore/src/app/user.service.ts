import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthorsigninComponent } from './authorsignin/authorsignin.component';

export enum Category{
  COMICS,HISTORY,FANTASY,HORROR
};
const isprod=true;
let URL ="http://localhost:8083/digitalbooks/";
if(isprod){
  URL="https://s0bbvlws31.execute-api.us-west-2.amazonaws.com/prod/";
}
@Injectable({//decorator
  providedIn: 'root'
})

export class UserService {
  book:any;
  slideShowFlag:boolean=true;
  createbooknavFlag:boolean=false;
  createBookContainerFlag:boolean=false;
  createbooksuccessContainerFlag:boolean=false;
  authorsignupNavFlag:boolean=true;
  mydigitalBooksContainerFlag:boolean=true;
  digitalBooksContainerFlag:boolean=true;
  editBookContainerFlag:boolean=false;
  editbooksuccessContainerFlag:boolean=false;
  updateBookPageFlag:boolean=false;
  hastoeditbook={
    id:Number,
    authorid:'',
    title:"",
    category:Category,
    author:"",
    price:"",
    publisher:"",
    publisheddate:"",
    chapters:"",
    active:Boolean,
    content:""
  };
  authorBooksContainerFlag: boolean=false;
  constructor(public http:HttpClient) { }
  signout(authorid: any) {
    return this.http.post(URL+"author/"+authorid+"/signout",null);
  }
  returnBook(book:any, readeremail:any) {
    return this.http.post(URL+"readers/"+readeremail+"/books/refund?bookid="+book.id,null);
  }
  readBookByPaymentID(reader:any) {
    return this.http.post(URL+"readers/"+reader.readeremail+"/books?paymentid="+reader.paymentId,null);
  }
  getMyBooksByReaderEmail(email:any) {
    return this.http.get(URL+"readers/"+email+"/books");
  }
  buybook(reader: any) {
   return this.http.post(URL+"books/buy",reader);
  }
  updateBook(book:any){
     return this.http.put(URL+"author/"+book.authorid+"/books?bookid="+book.id,book);
  }
  getAllBooks() {
    return this.http.get(URL+"allbooks");
  }
  searchBooks(params:any) {
    let path="books/search/?";
    if(!isprod){
      path="books/search?";
    }
    if(params.author!=""){
      path+="author="+params.author+"&";
    }
    if(params.price!=""){
      path+="price="+params.price+"&";
    }
    if(params.category!=""){
      path+="category="+params.category+"&";
    }
    if(params.publisher!=""){
      path+="publisher="+params.publisher;
    }
    return this.http.get(URL+path);
  }
  getbooksByAuthorID(): any {
   return this.http.get(URL+"books/"+sessionStorage.getItem('authorId'));
  }
  createBook(book: any) {
    return this.http.post(URL+"author/"+sessionStorage.getItem("authorId")+"/books",book);
  }
  authorSignin(loginRequest: { authorname: string; password: string; }) {
    return this.http.post(URL+"author/signin",loginRequest);
  }
  saveAuthor(author: any) {
    return this.http.post(URL+"author/signup",author);
  }
  getUsers(){
      return this.http.get(URL);
  }
}