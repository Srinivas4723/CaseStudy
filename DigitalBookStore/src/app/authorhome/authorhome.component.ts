import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-authorhome',
  templateUrl: './authorhome.component.html',
  styleUrls: ['./authorhome.component.css']
})
export class AuthorhomeComponent implements OnInit {
  searchbookdata={
    category:"",author:"",price:"",publisher:""
  }
  nobookFoundMessage:any;
  AllBooksOfAuthoId : any;
  authorBooks:any;
  
  updatebookblankResponse={
    title:'',
    category:'',
    author:'',
    price:'',
    publisher:'',
    publisheddate:'',
    chapters:'',
    active:'',
  }
  flag=false;
  authorName=sessionStorage.getItem("authorName");
  getmyBooksContainerFlag: boolean=true;
  readcontentbook: any;
  bookcontentFlag: boolean=false;
  authorBooksContainerFlag:boolean=true;
  constructor(public userService: UserService,public router:Router) { }
  readBook(book:any){
    this.authorBooksContainerFlag=false;
    this.readcontentbook=book;
   this.bookcontentFlag=true;
    
  }
  
  editbook(book:any){
    // const authorBookContainer:any = document.getElementById("authorBookContainer");
    // authorBookContainer.style.display="none";
    this.userService.hastoeditbook=book;
    console.log(JSON.stringify(book));
    this.authorBooksContainerFlag=false;
    this.userService.hastoeditbook=book;
    this.userService.editBookContainerFlag=true;
    this.userService.updateBookPageFlag=true;
    this.router.navigate(["/updatebook"]);

  }
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
      this.authorBooks=JSON.parse(JSON.stringify(error.error));
      }
    });
  }
  }
  ngOnInit(): void {
    console.log("C");
    this.userService.digitalBooksContainerFlag=false;
    this.userService.slideShowFlag=false;
    
    this.userService.createBookContainerFlag=false;
    // const authorloginContainer:any= document.getElementById("authorloginContainer");
    // authorloginContainer.style.display="none";
    // const authorhomeContainer:any=document.getElementById("authorhomeContainer");
    // authorhomeContainer.style.display="block";
    const observable=this.userService.getbooksByAuthorID();
    observable.subscribe((responseBody:any)=>{
     
      this.authorBooks=JSON.parse(JSON.stringify(responseBody));
    },
    (error:any)=>{
      
    })
  }

}


