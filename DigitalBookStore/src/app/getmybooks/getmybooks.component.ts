import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-getmybooks',
  templateUrl: './getmybooks.component.html',
  styleUrls: ['./getmybooks.component.css']
})
export class GetmybooksComponent implements OnInit {
  searchbookdata={
    category:"",author:"",price:"",publisher:""
  }
  reader={
    
    readeremail:"a@b.c",
    paymentId:""
  }
  readerBooks:any;
  readerblankResponse={
    
    readeremail:"",
    paymentId:""
  }
  readcontentbook={
    id:"",
    authorid:"",
    title:'',
    category:'',
    author:"",
    price:'',
    publisher:'',
    publisheddate:'',
    chapters:'',
    active:"",
  }
  Books:any=null;
  nobookFoundMessage:any;
  bookUnpurchaseSuccesMessage:any;
  getmyBooksContainerFlag:boolean=true;
  bookcontentFlag:boolean=false;
  constructor(public userService:UserService) { }
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
  returnBook(book:any){
    if(confirm("Are you sure , You want to return book")){
      const observable=this.userService.returnBook(book,this.reader.readeremail);
        observable.subscribe((responseBody)=>{
          console.log(responseBody);
          
          this.getmybooks();
        },
        (error:any)=>{
          if(typeof error.error!=="string"){
            this.bookUnpurchaseSuccesMessage=error.error.text;
            this.getmybooks();
          }
          console.log(error.error);
        });
    }
  }
  readBook(book:any){
    this.getmyBooksContainerFlag=false;
    this.readcontentbook=book;
   this.bookcontentFlag=true;
    
  }
  getmybooks(){
  this.readerBooks=null;
  // const contentofbook:any=document.getElementById("contentofbook");
  //   contentofbook.style.display="none";
  if(this.reader.readeremail!=="" && this.reader.paymentId===""){
  const observable=this.userService.getMyBooksByReaderEmail(this.reader.readeremail);
  observable.subscribe((responseBody)=>{
    console.log("R"+JSON.stringify(responseBody));
   this.getmyBooksContainerFlag=true;
    this.readerBooks=responseBody;
  },
  (error:any)=>{
    console.log("E"+JSON.stringify(error.error));
    if(typeof error.error==="string"){
      this.readerblankResponse.readeremail=error.error;
    }
  }
  );
}
else if(this.reader.readeremail!=="" && this.reader.paymentId!==""){
  const observable=this.userService.readBookByPaymentID(this.reader);
  observable.subscribe((responseBody)=>{
    console.log("R"+JSON.stringify(responseBody));
    // const getmybooksContainer:any=document.getElementById("getmybooksContainer");
    // getmybooksContainer.style.display="none";
    // const contentofbook:any=document.getElementById("contentofbook");
    // contentofbook.style.display="block";
    this.getmyBooksContainerFlag=true;
    this.readerBooks=[responseBody];
    // this.readcontentbook=JSON.parse(JSON.stringify(responseBody));
   
  },
  (error:any)=>{
    console.log("E"+JSON.stringify(error.error));
    if(typeof error.error==="string"){
      this.readerblankResponse.readeremail=error.error;
    }
  }
  );
}

  else{
   this.readerblankResponse.readeremail="Email ID Cannot be Blank";
  }
}
  ngOnInit(): void {
    this.userService.digitalBooksContainerFlag=false;
    this.userService.slideShowFlag=false;
    this.bookcontentFlag=false;
  }

}
