import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService,Category } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  bookcategory=Object.values(Category).filter(value => typeof value==="string");
  slidingImageFlags=[true,false,false,false];
  slideIndex:any = 0;
  searchbookdata={
    category:"",author:"",price:"",publisher:""
  }
  Books:any=null;
    nobookFoundMessage:any;
    signoutSuccessMessage: string="";
    title: any;
  constructor(public userService:UserService,public router:Router){}
  searchBooks(){
    this.nobookFoundMessage="";
    if(this.searchbookdata.author==="" && this.searchbookdata.category===null &&
        this.searchbookdata.price==="" &&  this.searchbookdata.publisher===""){
      alert("Search Fields Cannnot be Blank");
    }
    else{
      const observable= this.userService.searchBooks(this.searchbookdata);
      console.log("test"+JSON.stringify(this.searchbookdata));
      observable.subscribe((responseBody)=>{
        console.log(responseBody);
      },(error:any)=>{
        if(typeof error.error==='string'){
          alert("No Books Found with Your Filter....!!!!!");
          this.nobookFoundMessage=error.error;
        }
        else{
          this.Books=JSON.parse(JSON.stringify(error.error));
        }
      });
    }
  }
  showSlidingImages(n:any) {
    this.slidingImageFlags[this.slideIndex]=!this.slidingImageFlags[this.slideIndex];
    let i=this.slideIndex+=n;
    if (i >3) {this.slideIndex = 0}
    if (i < 0) {this.slideIndex = 3}
    this.slidingImageFlags[this.slideIndex]=!this.slidingImageFlags[this.slideIndex];
  }
  signout(){
    const observable=this.userService.signout(sessionStorage.getItem("authorId"));
    observable.subscribe((responseBody:any)=>{
      console.log(JSON.stringify(responseBody));
    },
    (error:any)=>{
      console.log(JSON.stringify(error));
    })
    sessionStorage.removeItem('authorId');
    sessionStorage.removeItem("authorName");
    this.userService.createbooknavFlag=false;
    this.userService.authorsignupNavFlag=true;
    this.signoutSuccessMessage="Sign Out Success";
    this.ngOnInit();
  }
  buyBook(book:any){
    this.userService.digitalBooksContainerFlag=false;
    this.userService.slideShowFlag=false;
    sessionStorage.setItem("buyingbook",book);
    this.userService.book=book;
    this.router.navigate(['/readerpage']);
  }
  ngOnInit() { 
    this.userService.authorBooksContainerFlag=false; 
    this.userService.editbooksuccessContainerFlag=false;
    this.userService.updateBookPageFlag=false;
    this.userService.editBookContainerFlag=false;
    this.signoutSuccessMessage="";
    this.userService.slideShowFlag=true;
    this.userService.digitalBooksContainerFlag=true; 
    if(sessionStorage.getItem("authorId")!==null){
      this.userService.authorsignupNavFlag=false;
      this.userService.createbooknavFlag=true;
    }
    const observable=this.userService.getAllBooks();
    observable.subscribe((responseBody:any)=>{
      if(responseBody.length===0){
        
        this.nobookFoundMessage="No Books Avalable in the Store";
      }
      else{
        this.Books=responseBody;
      }
    });
  }
}