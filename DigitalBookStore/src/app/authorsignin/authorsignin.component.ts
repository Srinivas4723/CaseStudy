import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-authorsignin',
  templateUrl: './authorsignin.component.html',
  styleUrls: ['./authorsignin.component.css']
})
export class AuthorsigninComponent implements OnInit {

  loginRequest ={
    authorname:"abc",
    password:"abc"

  }
  signInBlankResponse ={
    authorname:"",
    password:""
  }
  signInIvalidResponse:any;
  
  constructor(public userService: UserService,public router:Router) { }
  
  authorSignIn(){
    const observable= this.userService.authorSignin(this.loginRequest);
    observable.subscribe((responseBody:any)=>{
      console.log("RB"+responseBody);
      this.signInBlankResponse.authorname=responseBody.authorname;
      this.signInBlankResponse.password=responseBody.password;
      
      
      //this.signInResponse=JSON.stringify(responseBody);
    },
    (error:any)=>{
      console.log("eR"+JSON.stringify(error.error));
      if(typeof error.error==='string'){
      this.signInIvalidResponse=error.error;
      }
      else{
        console.log(error.error.text);
       sessionStorage.setItem("authorName",this.loginRequest.authorname);
        sessionStorage.setItem('authorId',error.error.text.replace("Author Login Success",""));
        console.log(sessionStorage.getItem('authorId'));
        this.router.navigate(['/authorhome']);
      }
    });
  }
  ngOnInit(): void {
    const digitalBooksContainer:any=document.getElementById("digitalBooksContainer");
    digitalBooksContainer.style.display="none";
  }

}
