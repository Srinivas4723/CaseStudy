import { Component, OnInit } from '@angular/core';
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
  Books:any;
  hastoeditbook={
    id:'',
    authorid:"",
    title:'',
    category:'',
    author:'',
    price:'',
    publisher:'',
    publisheddate:'',
    chapters:'',
    active:'',
  };
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
  constructor(public userService: UserService) { }
  updateBook(){
    const observable= this.userService.updateBook(this.hastoeditbook);
    observable.subscribe((responseBody:any)=>{
      console.log("R"+JSON.stringify(responseBody));
    },
    (error:any)=>{
      console.log("E"+JSON.stringify(error.error));
     if(typeof error.error.text==="string"){
      const authorBookContainer:any = document.getElementById("authorBookContainer");
      authorBookContainer.style.display="block";
      const editBookContainer:any=document.getElementById("editBookContainer");
      editBookContainer.style.display="none";
      this.flag=false;
      console.log("E"+JSON.stringify(error.error));
       this.nobookFoundMessage=error.error.text;
     }
    })
  }
  editbook(book:any){
    const authorBookContainer:any = document.getElementById("authorBookContainer");
    authorBookContainer.style.display="none";
    this.hastoeditbook=book;
    console.log(JSON.stringify(book));
    this.flag=true;

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
      this.Books=JSON.parse(JSON.stringify(error.error));
      }
    });
  }
  }
  ngOnInit(): void {
    
    // const authorloginContainer:any= document.getElementById("authorloginContainer");
    // authorloginContainer.style.display="none";
    // const authorhomeContainer:any=document.getElementById("authorhomeContainer");
    // authorhomeContainer.style.display="block";
    const observable=this.userService.getbooksByAuthorID();
    observable.subscribe((responseBody:any)=>{
     
      this.Books=JSON.parse(JSON.stringify(responseBody));
    },
    (error:any)=>{
      
    })
  }

}


