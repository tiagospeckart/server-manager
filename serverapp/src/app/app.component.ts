import { Component, OnInit } from '@angular/core';
import { ServerService } from './service/server.service';
import { Observable, catchError, map, of, startWith } from 'rxjs';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { DataState } from './enum/data.state.enum';
import { faRoute, faTrash, faCirclePlus, faTableTennisPaddleBall } from '@fortawesome/free-solid-svg-icons'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  // FA Icons
  faRoute = faRoute;
  faThrash = faTrash;
  faCirclePlus = faCirclePlus;
  faTableTennisPaddleBall = faTableTennisPaddleBall;

  appState$: Observable<AppState<CustomResponse>>;
  constructor(private serverService: ServerService) {}

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$.pipe(
      map((response) => {
        return { dataState: DataState.LOADED_STATE, appData: response };
      }),
      startWith({ dataState: DataState.LOADING_STATE }),
      catchError((error: string) => {
        // of operator returns an Observable of same type
        return of({ dataState: DataState.ERROR_STATE, error });
      })
    );
  }
}
