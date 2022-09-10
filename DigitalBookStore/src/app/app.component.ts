import { analyzeAndValidateNgModules } from '@angular/compiler';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title(title: any) {
    throw new Error('Method not implemented.');
  }
  //////slide show
slidingImageFlags=[true,false,false,false];
slideIndex:any = 0;
searchbookdata={
  category:"",author:"",price:"",publisher:""
}
Books:any=null;
  nobookFoundMessage:any;
  signoutSuccessMessage: string="";
//flags

// authorhomeContainerFlag:boolean=false;
// authorloginContainerFalg:boolean=false;
//this.showDivs(slideIndex);
searchBooks(){
  if(this.searchbookdata.author==="" &&
  this.searchbookdata.category==="" &&
  this.searchbookdata.price==="" &&
  this.searchbookdata.publisher===""){
    alert("Search Fields Cannnot be Blank");
  }
  else{
  const observable= this.userService.searchBooks(this.searchbookdata);
  observable.subscribe((responseBody:any)=>{
   
  },
  (error:any)=>{
    if(typeof error.error==='string'){
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

  ////slide show
  
  constructor(public userService:UserService,public router:Router){}
  signout(){
    sessionStorage.removeItem('authorId');
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
        this.nobookFoundMessage="No Book Avalable in the Store";
      }
      else{
        this.Books=responseBody;
      }
    });
    
    if(sessionStorage.getItem('authorId')!==null){//authorhome
      
    // const authorhomeContainer:any = document.getElementById("authorhomeContainer");
    // authorhomeContainer.style.display="block";
    //   const authorloginContainer:any=document.getElementById("authorloginContainer");
    //   authorloginContainer.style.display="none";
     
  }
  else{//Home

    // const authorhomeContainer:any = document.getElementById("authorhomeContainer");
    // authorhomeContainer.style.display="none";
    //   const authorloginContainer:any=document.getElementById("authorloginContainer");
    //   authorloginContainer.style.display="block";
     
      
  }
  
    
  }
}
