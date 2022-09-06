import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetmybooksComponent } from './getmybooks.component';

describe('GetmybooksComponent', () => {
  let component: GetmybooksComponent;
  let fixture: ComponentFixture<GetmybooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetmybooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GetmybooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
