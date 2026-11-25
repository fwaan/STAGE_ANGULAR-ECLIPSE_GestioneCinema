import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FilmNavBar } from './film-nav-bar';

describe('FilmNavBar', () => {
  let component: FilmNavBar;
  let fixture: ComponentFixture<FilmNavBar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilmNavBar],
    }).compileComponents();

    fixture = TestBed.createComponent(FilmNavBar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
