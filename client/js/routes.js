
export default {
  home: {
    path: '/',
    controller: 'homeController'
  },
  celebs: {
    path: '/celebs',
    controller: 'celebController'
  },
  films: {
    path: '/movies',
    controller: 'movieController'
  },
  tvShows: {
    path: '/tvShows',
    controller: 'tvShowController'
  },
  auth: {
    path: '/auth', 
    controller: 'authenticationController',
  },
  user: {
    path: '/user',
    controller: 'userController',
  },
  favorites: {
    path: '/favorites',
    controller: 'favoriteController',
  },
  currentPath: {
    path: '',
    controller: ''
  }
};
