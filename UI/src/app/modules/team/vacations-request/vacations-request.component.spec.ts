import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VacationsRequestComponent } from './vacations-request.component';

describe('VacationsRequestComponent', () => {
  let component: VacationsRequestComponent;
  let fixture: ComponentFixture<VacationsRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VacationsRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VacationsRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
