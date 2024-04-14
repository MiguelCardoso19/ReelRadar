function render(onClick) {
  const container = document.querySelector('#container');
  const button = document.createElement('button');
  const div = document.createElement('div');

  div.className = `text-center`;
  button.className = `btn btn-primary`;
  button.type = `button`;
  button.style = `margin-top: 10%; margin-bottom: 10%`;

  div.appendChild(button);

  container.innerHTML = ''; //removes the previous elements
  button.innerText = `CLICK ME FOR RANDOM MOVIE`;

  button.addEventListener('click', async e => {
    e.preventDefault();

    const {
      title,
      year,
      director,
      imdbRating: rating
    } = await onClick(parseInt(Math.random() * 6));
    const elem = document.createElement('div');
    elem.className = `text-center`;

    elem.innerHTML = `<h1>${title}</h1>
        <h3>${year}</h3>
        <h3>${director}</h3>
        <h3>${rating}</h3>`;

    if (container.childElementCount > 1) {
      container.removeChild(container.lastChild);
    }

    container.appendChild(elem);
  });

  container.appendChild(div);
};

export default { render };
