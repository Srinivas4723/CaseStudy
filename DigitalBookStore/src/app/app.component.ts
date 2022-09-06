import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'DigitalBookStore';
  Books:any;
  nobookFoundMessage:any;
  constructor(public userService:UserService,public router:Router){}
  signout(){
    sessionStorage.removeItem('authorId');
    
    console.log("x"+sessionStorage.getItem('authorId')+this.Books);
    this.ngOnInit();
  }
  buyBook(book:any){
    sessionStorage.setItem("buyingbook",book);
    this.userService.book=book;
    this.router.navigate(['/readerpage']);
  }
  ngOnInit(): void {
    const observable=this.userService.getAllBooks();
    observable.subscribe((responseBody:any)=>{
      console.log("R"+JSON.stringify(responseBody));
      this.Books=JSON.parse(JSON.stringify(responseBody));
    },
    (error:any)=>{
      
    });
    const digitalBooksContainer:any=document.getElementById("digitalBooksContainer");
    digitalBooksContainer.style.display="block";
    if(sessionStorage.getItem('authorId')!==null){//authorhome
      console.log("a"+sessionStorage.getItem('authorId'));
    const authorhomeContainer:any = document.getElementById("authorhomeContainer");
    authorhomeContainer.style.display="block";
      const authorloginContainer:any=document.getElementById("authorloginContainer");
      authorloginContainer.style.display="none";
     
  }
  else{//Home

    const authorhomeContainer:any = document.getElementById("authorhomeContainer");
    authorhomeContainer.style.display="none";
      const authorloginContainer:any=document.getElementById("authorloginContainer");
      authorloginContainer.style.display="block";
     
      
  }
  
    
  }
}
