import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthorsigninComponent } from './authorsignin/authorsignin.component';

const URL ="http://localhost:8083/api/v1/digitalbooks/"
@Injectable({//decorator
  providedIn: 'root'
})
export class UserService {
  getMyBooks() {
    return this.http.
  }
  buybook(reader: any) {
   return this.http.post(URL+"books/buy",reader);
  }
  book:any;
  updateBook(book:any){
     return this.http.put(URL+"author/"+book.authorid+"/books/"+book.id,book);
    
  }
  // signout() {
  //   return this.http.post(URL+"author/signout",{"authorname":sessionStorage.getItem("authorName"),"password":"p"});
  // }
  getAllBooks() {
    return this.http.get(URL+"allbooks");
  }
  searchBooks(params:any) {
    let path:any="books/search?";
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
  
   
  
  
  createBook(book: { title: string; category: string; author: string; publisher: string; publisheddate: string; chapters: string; active: string; }) {
    return this.http.post(URL+"author/"+sessionStorage.getItem("authorId")+"/books",book);
  }
  authorSignin(loginRequest: { authorname: string; password: string; }) {
    return this.http.post(URL+"author/signin",loginRequest);
  }
    delete(id: any){
        return this.http.delete(URL+"/delete/"+id)
    }
  saveAuthor(author: any) {
    return this.http.post(URL+"author/signup",author);
  }
  getUsers(){
      return this.http.get(URL);
  }
  
  constructor(public http:HttpClient) { }

}