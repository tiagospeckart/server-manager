import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subscriber, catchError, tap, throwError } from 'rxjs';
import { CustomResponse } from '../interface/custom-response';
import { Server } from '../interface/server';
import { Status } from '../enum/status.enum';

@Injectable({ providedIn: 'root' })
export class ServerService {
  private readonly apiUrl = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }

  servers$: Observable<CustomResponse> = 
  this.http.get<CustomResponse>(`${this.apiUrl}/server/list`)
    .pipe(
      tap(console.log),
      catchError(this.handleError.bind(this))
    );

  save$ = (server: Server): Observable<CustomResponse> => 
  this.http.post<CustomResponse>(`${this.apiUrl}/server/save`, server)
  .pipe(
    tap(console.log),
    catchError(this.handleError.bind(this))
  );

  ping$ = (ipAddress: string): Observable<CustomResponse> => 
  this.http.get<CustomResponse>(`${this.apiUrl}/server/ping/${ipAddress}`)
  .pipe(
    tap(console.log),
    catchError(this.handleError.bind(this))
  );

  // Recieves Status as filter parameter
  filter$ = (status: Status, response: CustomResponse): Observable<CustomResponse> => 
  // Creates a new Observable
  new Observable<CustomResponse>(
    // Callback function
    subscriber => {
      console.log(response);
      subscriber.next(
        // If Status is all, return an object with all avaiable servers
        status === Status.ALL ? { ...response, message: `Servers filtered by ${status} status` } :
        // Else, if status !ALL:
        { 
          // spreads response object
          ...response,

          // filter message string based on various scenarios
          message: response.data.servers
          // filter servers by status, then checks if response > 0
          // ternary operation to choose between two possible server status
          .filter(server => server.status === status).length > 0 ? `Servers filtered 
          by ${status === Status.SERVER_UP ? 'SERVER UP' : 'SERVER DOWN'} status` :
          // if 0 servers found
          `No server of ${status} found`,

          // filter Data object by server.status
          data: { servers: response.data.servers
            .filter(server => server.status === status)
          }
        }
      );
      subscriber.complete();
    }
  )
  .pipe(
    tap(console.log),
    catchError(this.handleError.bind(this))
  );

  delete$ = (serverId: number): Observable<CustomResponse> => 
  this.http.delete<CustomResponse>(`${this.apiUrl}/server/delete/${serverId}`)
  .pipe(
    tap(console.log),
    catchError(this.handleError.bind(this))
  );
  
  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error)
    return throwError(`An error occurred - Error code: ${error.status}`);
  }
}
